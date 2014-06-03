YAHOO.Bubbling.fire("registerAction", {
  actionName : "onWSCaller",
  fn : function onWSCaller(record, owner) {
    var actionId = owner.className;
    action = Alfresco.util.findInArray(record.actions, actionId, "id") || {};
    action = Alfresco.util.deepCopy(action);
    var params = action.params || {};
    for ( var key in params) {
      params[key] = YAHOO.lang.substitute(params[key], record, function getActionParams_substitute(p_key, p_value, p_meta) {
        return Alfresco.util.findValueByDotNotation(record, p_key);
      });
    }
    if (params && params.webscript) {
      window.location = Alfresco.constants.PROXY_URI + action.params.webscript;
    }
  }
});
