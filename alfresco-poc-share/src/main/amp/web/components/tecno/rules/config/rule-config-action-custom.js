if (typeof Tecno == "undefined" || !Tecno) {
   var Tecno = {};
}

/**
 * RuleConfigActionCustom.
 *
 * @namespace Tecno
 * @class Tecno.RuleConfigActionCustom
 */
(function() {

   /**
    * YUI Library aliases
    */
   var Dom = YAHOO.util.Dom,
      Selector = YAHOO.util.Selector,
      Event = YAHOO.util.Event;

   /**
    * Alfresco Slingshot aliases
    */
    var $html = Alfresco.util.encodeHTML,
       $hasEventInterest = Alfresco.util.hasEventInterest;
   
   Tecno.RuleConfigActionCustom = function(htmlId)
   {
      Tecno.RuleConfigActionCustom.superclass.constructor.call(this, htmlId);

      // Re-register with our own name
      this.name = "Tecno.RuleConfigActionCustom";
      Alfresco.util.ComponentManager.reregister(this);

      // Instance variables
      this.customisations = YAHOO.lang.merge(this.customisations, Tecno.RuleConfigActionCustom.superclass.customisations);
      this.renderers = YAHOO.lang.merge(this.renderers, Tecno.RuleConfigActionCustom.superclass.renderers);
      
      return this;
   };

   YAHOO.extend(Tecno.RuleConfigActionCustom, Alfresco.RuleConfigAction,
   {

      /**
       * CUSTOMISATIONS
       */

      customisations:
      {         
         MoveReplaced:
         {
            text: function(configDef, ruleConfig, configEl)
            {
	             // Display as path
	             this._getParamDef(configDef, "destination-folder")._type = "path";
	             return configDef;
            },
            edit: function(configDef, ruleConfig, configEl)
            {
                // Hide all parameters since we are using a cusotm ui but set default values
                this._hideParameters(configDef.parameterDefinitions);

                // Make parameter renderer create a "Destination" button that displays an destination folder browser
                configDef.parameterDefinitions.push({
                   type: "arca:destination-dialog-button",
                   displayLabel: this.msg("label.to"),
                   _buttonLabel: this.msg("button.select-folder"),
                   _destinationParam: "destination-folder"
                });
                return configDef;
            }
         },
      },

   });

})();
