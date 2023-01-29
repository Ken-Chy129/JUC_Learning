package cn.ken.flyweight;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/29 15:21
 */
@Slf4j
public class MyPool {
    
    private volatile static MyPool INSTANCE;
    
    private final int poolSize;
    private Connection[] connectionCache;
    private AtomicReferenceArray<Boolean> status;

    private MyPool(int poolSize) {
        this.poolSize = poolSize;
        connectionCache = new Connection[poolSize];
        status = new AtomicReferenceArray<>(new Boolean[poolSize]);
        for (int i=0; i<poolSize; i++) {
            this.connectionCache[i] = new MyConnection(String.valueOf(i));
        }
        for (int i=0; i<poolSize; i++) {
            this.status.set(i, false); 
        }
    }
    
    public static MyPool getINSTANCE(int poolSize) {
        if (INSTANCE == null) {
            synchronized (MyPool.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyPool(poolSize);
                }
            }
        }
        return INSTANCE;
    }
    
    public Connection getConnection() {
        while (true) {
            for (int i=0; i<this.poolSize; i++) {
                if (!status.get(i)) {
                    if (status.compareAndSet(i, false, true)) {
                        // 此处没有上锁，所以可能出现多个线程同时修改的问题，所以需要使用原子类
                        log.debug("持有连接{}", ((MyConnection)connectionCache[i]).getName());
                        return connectionCache[i];
                    }
                }
            }
            // 可能找不到connection来到此处后还没持有锁正好就有connection释放了，该线程此时还未进入等待队列所以感知不到，所以造成一直等待，所以wait需要有一个最大的等待时间
            synchronized (this) {
                try {
                    log.debug("等待线程");
                    this.wait(2000); // 因为connection一般是较长时间的操作，为了避免cpu空转，所以让线程先等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void free(Connection connection) {
        for (int i=0; i<this.poolSize; i++) {
            if (connection == connectionCache[i]) {
                status.set(i, false); // 此处只可能有一个线程
                synchronized (this) {
                    log.debug("释放连接{}", ((MyConnection)connectionCache[i]).getName());
                    this.notify();
                }
                break;
            }
        }        
    }

    public static void main(String[] args) {
        MyPool myPool = MyPool.getINSTANCE(3);
        for (int i=0; i<6; i++) {
            new Thread(() -> {
                Connection connection = myPool.getConnection();
                try {
                    Thread.sleep(1000); // 模拟使用连接操作数据库
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myPool.free(connection);
            }).start();
        }
    }
}

class MyConnection implements Connection {
    
    private String name;

    public MyConnection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return null;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return null;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return false;
    }

    @Override
    public void commit() throws SQLException {

    }

    @Override
    public void rollback() throws SQLException {

    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {

    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {

    }

    @Override
    public String getCatalog() throws SQLException {
        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {

    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return null;
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {

    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {

    }

    @Override
    public String getSchema() throws SQLException {
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
