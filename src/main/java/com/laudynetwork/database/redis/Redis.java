package com.laudynetwork.database.redis;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import lombok.Getter;

@Getter
public class Redis {

    @Getter
    public final StatefulRedisConnection<String, String> connection;
    private final RedisClient client;

    public Redis() {
        this.client = RedisClient.create("redis://RlUD7l3AC5Lg9UyC3BTjxIsn6Lw984TG@89.163.129.221:6379");
        this.connection = this.client.connect();
    }

    public void shutdown() {
        this.connection.close();
        this.client.shutdown();
    }
}

