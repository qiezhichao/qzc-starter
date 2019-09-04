package com.qzc.ag.handler.executor;

import com.qzc.ag.handler.message.Message;

public interface Executor {
    Object execute(Message message);
}
