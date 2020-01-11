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

public final class DetectObjectsCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(DetectObjectsCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    DetectObjects command = new DetectObjects();
    if(parameters.get("imagefile") == null || parameters.get("imagefile").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","imagefile"));
    }

    if(parameters.get("savefile") == null || parameters.get("savefile").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","savefile"));
    }

    if(parameters.get("xmlfile") == null || parameters.get("xmlfile").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","xmlfile"));
    }


    if(parameters.get("imagefile") != null && parameters.get("imagefile").get() != null && !(parameters.get("imagefile").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","imagefile", "String", parameters.get("imagefile").get().getClass().getSimpleName()));
    }
    if(parameters.get("savefile") != null && parameters.get("savefile").get() != null && !(parameters.get("savefile").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","savefile", "String", parameters.get("savefile").get().getClass().getSimpleName()));
    }
    if(parameters.get("xmlfile") != null && parameters.get("xmlfile").get() != null && !(parameters.get("xmlfile").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","xmlfile", "String", parameters.get("xmlfile").get().getClass().getSimpleName()));
    }
    if(parameters.get("noofobjects") != null && parameters.get("noofobjects").get() != null && !(parameters.get("noofobjects").get() instanceof Number)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","noofobjects", "Number", parameters.get("noofobjects").get().getClass().getSimpleName()));
    }
    try {
      Optional<Value> result =  Optional.ofNullable(command.action(parameters.get("imagefile") != null ? (String)parameters.get("imagefile").get() : (String)null ,parameters.get("savefile") != null ? (String)parameters.get("savefile").get() : (String)null ,parameters.get("xmlfile") != null ? (String)parameters.get("xmlfile").get() : (String)null ,parameters.get("noofobjects") != null ? (Number)parameters.get("noofobjects").get() : (Number)null ));
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
