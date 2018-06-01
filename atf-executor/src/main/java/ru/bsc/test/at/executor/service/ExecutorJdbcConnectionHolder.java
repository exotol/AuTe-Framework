package ru.bsc.test.at.executor.service;

import lombok.extern.slf4j.Slf4j;
import ru.bsc.test.at.executor.model.Stand;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Class for storing jdbc connection map for each project
 *
 * @author Pavel Golovkin
 */
@Slf4j
class ExecutorJdbcConnectionHolder implements Closeable {
    private Map<Stand, Connection> standConnectionMap = new ConcurrentHashMap<>();

    ExecutorJdbcConnectionHolder(Set<Stand> standSet) {
        for (Stand stand : standSet) {
            if (isNotEmpty(stand.getDbUrl())) {
                try {
                    Connection connection = DriverManager.getConnection(stand.getDbUrl(), stand.getDbUser(), stand.getDbPassword());
                    connection.setAutoCommit(false);
                    connection.setReadOnly(true);
                    standConnectionMap.put(stand, connection);
                } catch (SQLException e) {
                    log.warn("sql exception", e);
                    standConnectionMap.put(stand, null);
                }
            }
        }
    }

    Connection getConnection(Stand stand) {
        return standConnectionMap.get(stand);
    }

    @Override
    public void close() {
        standConnectionMap.values().stream().filter(Objects::nonNull).forEach(connection -> {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error("Error while rollback", e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error while close connection", e);
                }
            }
        });

    }
}
