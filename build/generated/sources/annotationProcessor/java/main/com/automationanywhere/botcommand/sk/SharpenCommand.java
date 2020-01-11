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

public final class SharpenCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(SharpenCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    Sharpen command = new Sharpen();
    if(parameters.get("imagefile") == null || parameters.get("imagefile").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","imagefile"));
    }

    if(parameters.get("savefile") == null || parameters.get("savefile").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","savefile"));
    }

    if(parameters.get("sigma") == null || parameters.get("sigma").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","sigma"));
    }

    if(parameters.get("alpha") == null || parameters.get("alpha").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","alpha"));
    }

    if(parameters.get("beta") == null || parameters.get("beta").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","beta"));
    }

    if(parameters.get("gamma") == null || parameters.get("gamma").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","gamma"));
    }

    if(parameters.get("imagefile") != null && parameters.get("imagefile").get() != null && !(parameters.get("imagefile").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","imagefile", "String", parameters.get("imagefile").get().getClass().getSimpleName()));
    }
    if(parameters.get("savefile") != null && parameters.get("savefile").get() != null && !(parameters.get("savefile").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","savefile", "String", parameters.get("savefile").get().getClass().getSimpleName()));
    }
    if(parameters.get("sigma") != null && parameters.get("sigma").get() != null && !(parameters.get("sigma").get() instanceof Number)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","sigma", "Number", parameters.get("sigma").get().getClass().getSimpleName()));
    }
    if(parameters.get("alpha") != null && parameters.get("alpha").get() != null && !(parameters.get("alpha").get() instanceof Number)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","alpha", "Number", parameters.get("alpha").get().getClass().getSimpleName()));
    }
    if(parameters.get("beta") != null && parameters.get("beta").get() != null && !(parameters.get("beta").get() instanceof Number)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","beta", "Number", parameters.get("beta").get().getClass().getSimpleName()));
    }
    if(parameters.get("gamma") != null && parameters.get("gamma").get() != null && !(parameters.get("gamma").get() instanceof Number)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","gamma", "Number", parameters.get("gamma").get().getClass().getSimpleName()));
    }
    try {
      command.action(parameters.get("imagefile") != null ? (String)parameters.get("imagefile").get() : (String)null ,parameters.get("savefile") != null ? (String)parameters.get("savefile").get() : (String)null ,parameters.get("sigma") != null ? (Number)parameters.get("sigma").get() : (Number)null ,parameters.get("alpha") != null ? (Number)parameters.get("alpha").get() : (Number)null ,parameters.get("beta") != null ? (Number)parameters.get("beta").get() : (Number)null ,parameters.get("gamma") != null ? (Number)parameters.get("gamma").get() : (Number)null );Optional<Value> result = Optional.empty();
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
