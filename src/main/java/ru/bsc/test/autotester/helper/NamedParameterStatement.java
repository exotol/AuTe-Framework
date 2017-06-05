package ru.bsc.test.autotester.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 05.06.2017.
 *
 * http://www.javaworld.com/article/2077706/core-java/named-parameters-for-preparedstatement.html
 *
 */
public class NamedParameterStatement implements AutoCloseable {
    private final PreparedStatement statement;
    private final Map<String, List<Integer>> indexMap;

    public NamedParameterStatement(Connection connection, String query) throws SQLException {
        indexMap=new HashMap<>();
        String parsedQuery=parse(query, indexMap);
        statement=connection.prepareStatement(parsedQuery);
    }

    private static String parse(String query, Map<String, List<Integer>> paramMap) {
        // I was originally using regular expressions, but they didn't work well for ignoring
        // parameter-like strings inside quotes.
        int length=query.length();
        StringBuilder parsedQuery = new StringBuilder(length);
        boolean inSingleQuote=false;
        boolean inDoubleQuote=false;
        int index=1;

        for(int i=0;i<length;i++) {
            char c=query.charAt(i);
            if(inSingleQuote) {
                if(c=='\'') {
                    inSingleQuote=false;
                }
            } else if(inDoubleQuote) {
                if(c=='"') {
                    inDoubleQuote=false;
                }
            } else {
                if(c=='\'') {
                    inSingleQuote=true;
                } else if(c=='"') {
                    inDoubleQuote=true;
                } else if(c==':' && i+1<length &&
                        Character.isJavaIdentifierStart(query.charAt(i+1))) {
                    int j=i+2;
                    while(j<length && Character.isJavaIdentifierPart(query.charAt(j))) {
                        j++;
                    }
                    String name=query.substring(i+1,j);
                    c='?'; // replace the parameter with a question mark
                    i+=name.length(); // skip past the end if the parameter

                    List<Integer> indexList=paramMap.get(name);
                    if(indexList==null) {
                        indexList=new LinkedList<>();
                        paramMap.put(name, indexList);
                    }
                    indexList.add(index);

                    index++;
                }
            }
            parsedQuery.append(c);
        }

        // replace the lists of Integer objects with arrays of ints

        // replace the lists of Integer objects with arrays of ints
        /*
        for(Iterator itr=paramMap.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry=(Map.Entry)itr.next();
            List list=(List)entry.getValue();
            int[] indexes=new int[list.size()];
            int i=0;
            for(Iterator itr2=list.iterator(); itr2.hasNext();) {
                Integer x=(Integer)itr2.next();
                indexes[i++]=x.intValue();
            }
            entry.setValue(indexes);
        }
        */

        /*
        for (List<Integer> list : paramMap.values()) {
            list.addAll(list);
        }
        */

        return parsedQuery.toString();
    }

    public void setString(String name, String value) throws SQLException {
        List<Integer> indexList = indexMap.get(name);
        if (indexList != null) {
            for (Integer index : indexList) {
                statement.setString(index, value);
            }
        }
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    public boolean execute() throws SQLException {
        return statement.execute();
    }

    public ResultSet executeQuery() throws SQLException {
        return statement.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public void close() throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    public void addBatch() throws SQLException {
        statement.addBatch();
    }

    public int[] executeBatch() throws SQLException {
        return statement.executeBatch();
    }
}
