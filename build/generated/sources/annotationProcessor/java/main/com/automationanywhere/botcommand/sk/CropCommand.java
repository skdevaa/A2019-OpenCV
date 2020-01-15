package com.automationanywhere.botcommand.sk;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Number;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CropCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(CropCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    Crop command = new Crop();
    if(parameters.get("imagefile") == null || parameters.get("imagefile").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","imagefile"));
    }

    if(parameters.get("savefile1") == null || parameters.get("savefile1").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","savefile1"));
    }



    if(parameters.get("threshold") == null || parameters.get("threshold").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","threshold"));
    }

    if(parameters.get("imagefile") != null && parameters.get("imagefile").get() != null && !(parameters.get("imagefile").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","imagefile", "String", parameters.get("imagefile").get().getClass().getSimpleName()));
    }
    if(parameters.get("savefile1") != null && parameters.get("savefile1").get() != null && !(parameters.get("savefile1").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","savefile1", "String", parameters.get("savefile1").get().getClass().getSimpleName()));
    }
    if(parameters.get("savefile2") != null && parameters.get("savefile2").get() != null && !(parameters.get("savefile2").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","savefile2", "String", parameters.get("savefile2").get().getClass().getSimpleName()));
    }
    if(parameters.get("savefile3") != null && parameters.get("savefile3").get() != null && !(parameters.get("savefile3").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","savefile3", "String", parameters.get("savefile3").get().getClass().getSimpleName()));
    }
    if(parameters.get("threshold") != null && parameters.get("threshold").get() != null && !(parameters.get("threshold").get() instanceof Number)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","threshold", "Number", parameters.get("threshold").get().getClass().getSimpleName()));
    }
    try {
      command.action(parameters.get("imagefile") != null ? (String)parameters.get("imagefile").get() : (String)null ,parameters.get("savefile1") != null ? (String)parameters.get("savefile1").get() : (String)null ,parameters.get("savefile2") != null ? (String)parameters.get("savefile2").get() : (String)null ,parameters.get("savefile3") != null ? (String)parameters.get("savefile3").get() : (String)null ,parameters.get("threshold") != null ? (Number)parameters.get("threshold").get() : (Number)null );Optional<Value> result = Optional.empty();
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
