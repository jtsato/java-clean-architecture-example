package io.github.jtsato.bookstore.dataprovider.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * @author Jorge Takeshi Sato
 */

class PrettySqlFormatTest {

    @DisplayName("Successful to prettify SELECT SQL if it is supplied")
    @Test
    void successfulToPrettifySelectSQLIfItIsSupplied() {

        final MessageFormattingStrategy prettySqlFormat = new PrettySqlFormat();

        final int connectionId = 1;
        final String now = "2020-08-23 00:27:44";
        long elapsed = 100L;
        final String category = Category.STATEMENT.getName();
        final String prepared = null;
        final String sql = "select id, name from table where id is not null and name like '%dummy%'";
        final String url = null;

        final String prettySql = prettySqlFormat.formatMessage(connectionId, now, elapsed, category, prepared, sql, url);

        assertThat(prettySql).isNotBlank();

        final String[] result = StringUtils.split(prettySql, "\r\n");

        assertThat(result).hasSize(10);
        assertThat(result[0]).isEqualTo(String.format("%s|%dms|%s|connection %d|", now, elapsed, category, connectionId));
        assertThat(result[1]).isEqualTo("PrettySqlFormat(P6Spy SQL,Hibernate format):");
        assertThat(result[2]).isEqualTo("    select");
        assertThat(result[3]).isEqualTo("        id,");
        assertThat(result[4]).isEqualTo("        name ");
        assertThat(result[5]).isEqualTo("    from");
        assertThat(result[6]).isEqualTo("        table ");
        assertThat(result[7]).isEqualTo("    where");
        assertThat(result[8]).isEqualTo("        id is not null ");
        assertThat(result[9]).isEqualTo("        and name like '%dummy%'");
    }

    @DisplayName("Successful to prettify CREATE SQL if it is supplied")
    @Test
    void successfulToPrettifyCreateSQLIfItIsSupplied() {

        final MessageFormattingStrategy prettySqlFormat = new PrettySqlFormat();

        final int connectionId = 1;
        final String now = "2020-08-23 00:27:44";
        long elapsed = 100L;
        final String category = Category.STATEMENT.getName();
        final String prepared = null;
        final String sql = "create table dummy (id bigint, name varchar(255) not null, creation_date timestamp not null, update_date timestamp not null, primary key (id));";
        final String url = null;

        final String prettySql = prettySqlFormat.formatMessage(connectionId, now, elapsed, category, prepared, sql, url);

        assertThat(prettySql).isNotBlank();

        final String[] result = StringUtils.split(prettySql, "\r\n");

        assertThat(result).hasSize(9);
        assertThat(result[0]).isEqualTo(String.format("%s|%dms|%s|connection %d|", now, elapsed, category, connectionId));
        assertThat(result[1]).isEqualTo("PrettySqlFormat(P6Spy SQL,Hibernate format):");
        assertThat(result[2]).isEqualTo("    create table dummy (");
        assertThat(result[3]).isEqualTo("       id bigint,");
        assertThat(result[4]).isEqualTo("        name varchar(255) not null,");
        assertThat(result[5]).isEqualTo("        creation_date timestamp not null,");
        assertThat(result[6]).isEqualTo("        update_date timestamp not null,");
        assertThat(result[7]).isEqualTo("        primary key (id)");
        assertThat(result[8]).isEqualTo("    );");
    }
    
    @DisplayName("Successful to prettify SQL if it is not supplied")
    @Test
    void successfulToPrettifySQLIfItIsNotSupplied() {

        final MessageFormattingStrategy prettySqlFormat = new PrettySqlFormat();

        final int connectionId = 1;
        final String now = "2020-08-23 00:27:44";
        long elapsed = 100L;
        final String category = Category.STATEMENT.getName();
        final String prepared = null;
        final String sql = " ";
        final String url = null;

        final String prettySql = prettySqlFormat.formatMessage(connectionId, now, elapsed, category, prepared, sql, url);

        final String[] result = StringUtils.split(prettySql, "\r\n");

        assertThat(result).hasSize(1);
        assertThat(result[0]).isEqualTo(String.format("%s|%dms|%s|connection %d|", now, elapsed, category, connectionId));
    }
    
    @DisplayName("Successful to prettify SQL if category is not statment")
    @Test
    void successfulToPrettifySqlIfCategoryIsNotStatement() {

        final MessageFormattingStrategy prettySqlFormat = new PrettySqlFormat();

        final String now = "2020-08-23 00:27:44";
        long elapsed = 0L;
        final String category = Category.COMMIT.getName();
        final String prepared = null;
        final String sql = "commit";
        final String url = null;

        final String prettySql = prettySqlFormat.formatMessage(0, now, elapsed, category, prepared, sql, url);
        final String[] splittedPrettySql = StringUtils.split(prettySql, "\r\n");

        assertThat(splittedPrettySql).hasSize(1);
        assertThat(splittedPrettySql[0]).isEqualTo(String.format("%s|%dms|%s|connection %d|commit", now, elapsed, category, 0));
    }

}
