package com.automationanywhere.botcommand.sk;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ConcatenateCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(ConcatenateCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    Concatenate command = new Concatenate();
    if(parameters.get("imagefile1") == null || parameters.get("imagefile1").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","imagefile1"));
    }

    if(parameters.get("imagefile2") == null || parameters.get("imagefile2").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","imagefile2"));
    }

    if(parameters.get("savefile") == null || parameters.get("savefile").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","savefile"));
    }

    if(parameters.get("layout") == null || parameters.get("layout").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","layout"));
    }
    if(parameters.get("layout") != null && parameters.get("layout").get() != null && !(parameters.get("layout").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","layout", "String", parameters.get("layout").get().getClass().getSimpleName()));
    }
    if(parameters.get("layout") != null) {
      switch((String)parameters.get("layout").get()) {
        case "VERTICAL" : {

        } break;
        case "HORIZONTAL" : {

        } break;
        default : throw new BotCommandException(MESSAGES_GENERIC.getString("generic.InvalidOption","layout"));
      }
    }

    if(parameters.get("imagefile1") != null && parameters.get("imagefile1").get() != null && !(parameters.get("imagefile1").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","imagefile1", "String", parameters.get("imagefile1").get().getClass().getSimpleName()));
    }
    if(parameters.get("imagefile2") != null && parameters.get("imagefile2").get() != null && !(parameters.get("imagefile2").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","imagefile2", "String", parameters.get("imagefile2").get().getClass().getSimpleName()));
    }
    if(parameters.get("savefile") != null && parameters.get("savefile").get() != null && !(parameters.get("savefile").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","savefile", "String", parameters.get("savefile").get().getClass().getSimpleName()));
    }
    if(parameters.get("layout") != null && parameters.get("layout").get() != null && !(parameters.get("layout").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","layout", "String", parameters.get("layout").get().getClass().getSimpleName()));
    }
    try {
      command.action(parameters.get("imagefile1") != null ? (String)parameters.get("imagefile1").get() : (String)null ,parameters.get("imagefile2") != null ? (String)parameters.get("imagefile2").get() : (String)null ,parameters.get("savefile") != null ? (String)parameters.get("savefile").get() : (String)null ,parameters.get("layout") != null ? (String)parameters.get("layout").get() : (String)null );Optional<Value> result = Optional.empty();
      logger.traceExit(result);
      return result;
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","action"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }
}
