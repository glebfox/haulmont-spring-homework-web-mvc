package org.example.repository;

import org.apache.commons.lang3.StringUtils;
import org.example.domain.Order;
import org.example.domain.OrderStatus;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class OrderRepository {
    public static final String DEFAULT_RETURN_URL = "http://glebfox.com/return";
    public static final String DEFAULT_FAIL_URL = "http://glebfox.com/fail";

    private final JdbcTemplate template;

    private final RowMapper<Order> rowMapper = (rs, i) -> new Order(
            rs.getLong("id"),
            rs.getString("userName"),
            rs.getString("password"),
            rs.getString("orderNumber"),
            rs.getLong("amount"),
            rs.getInt("currency"),
            rs.getString("returnUrl"),
            rs.getString("failUrl"),
            rs.getString("status"),
            rs.getDate("deletedAt")
    );

    public OrderRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public Optional<Order> getById(long id) {
        // language=PostgreSQL
        return queryForOptional(
                "SELECT id, userName, password, orderNumber, amount, currency, returnUrl, failUrl, status, deletedAt " +
                        "FROM orders WHERE id = ? and deletedAt IS NULL",
                rowMapper, id
        );
    }

    public Optional<Order> save(Order order) {
        // language=PostgreSQL
        return queryForOptional(
                "INSERT INTO orders(" +
                        "userName, password, orderNumber, amount, currency, returnUrl, failUrl, status, deletedAt) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                        "RETURNING " +
                        "id, userName, password, orderNumber, amount, currency, returnUrl, failUrl, status, deletedAt",
                rowMapper,
                order.getUserName(),
                order.getPassword(),
                order.getOrderNumber(),
                order.getAmount(),
                order.getCurrency(),
                StringUtils.defaultString(order.getReturnUrl(), DEFAULT_RETURN_URL),
                StringUtils.defaultString(order.getFailUrl(), DEFAULT_FAIL_URL),
                OrderStatus.NEW.getId(),
                null
        );
    }

    public Optional<Order> update(Order order) {
        // language=PostgreSQL
        return queryForOptional(
                "UPDATE orders " +
                        "SET userName = ?, password = ?, orderNumber = ?, amount = ?, currency = ?, returnUrl = ?, " +
                        "failUrl = ?, status = ?, deletedAt = ? " +
                        "WHERE id = ? " +
                        "RETURNING " +
                        "id, userName, password, orderNumber, amount, currency, returnUrl, failUrl, status, deletedAt",
                rowMapper,
                order.getUserName(),
                order.getPassword(),
                order.getOrderNumber(),
                order.getAmount(),
                order.getCurrency(),
                order.getReturnUrl(),
                order.getFailUrl(),
                order.getStatus(),
                order.getDeletedAt(),
                order.getId()
        );
    }

    private <T> Optional<T> queryForOptional(String sql, RowMapper<T> rowMapper, Object... args) {
        return Optional.ofNullable(DataAccessUtils.singleResult(template.query(
                sql, rowMapper, args
        )));
    }

}
