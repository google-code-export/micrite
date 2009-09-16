Ext.ns('micrite.util');
micrite.util = function() {
    var success = function(r,o){
        var res = Ext.decode(r.responseText);
        if (res&&res.message){
            if (!res.success){
                showMsg('failure',res.message);
            }else{
                showMsg('success',res.message);
            }
        }
    };
    var failure = function(r,o){
        var res = Ext.decode(r.responseText);
        if (!res){
            showMsg('failure', 'No Response From Server');
        }else if (res.message){
            showMsg('failure', res.message);
        }
    };
    
    var callback = function(el,s,r,o){
        if (Ext.get('session-expired')||Ext.get('access-denied')){
            var res = Ext.decode(r.responseText);
            showMsg('failure', res.message);
        }
    };
    
     return {
         proxyLoad : function () {
             c ={listeners:{
                        loadexception:function(proxy, options, resp, error) {
                         var res = Ext.decode(resp.responseText);
                         if (!res){
                            showMsg('failure', 'No Response From Server');
                         }else if (res.message){
                                showMsg('failure',res.message);
                             }
                        }
                    }
                 };
             return c;
        },
        autoLoad : function (c){
                 c = Ext.apply(c,{
                     callback: callback
                     }
                 );
             return c;
         },
         genWindow : function(c){
              if (c.id && Ext.getCmp(c.id)) {
                Ext.getCmp(c.id).center();
                return;
            }
             if (c.autoLoad){
                 c.autoLoad = Ext.apply(c.autoLoad,{
                         callback: callback,
                         nocache: true
                     }
                 );
             }
             var pbid = Ext.id();
            var win = new Ext.Window(Ext.apply({
                    closable : true,
                    border   : false,
                    width    : 640,
                    height   : 520,
                    plain    : true,
                    maximizable: true,
                    html:    '<div id="'+pbid+'" style="height:100%;"></div>',
                    layout   : 'fit'
                },c));
            win.show();
            win.center();
            var myMask = new Ext.LoadMask(Ext.get(pbid), {msg:mbLocale.loadingMsg});
            myMask.show();
            return win;
        },
        ajaxRequest : function (c,scope){
            c.success = success.createSequence(c.success,scope);
            c.failure = failure.createSequence(c.failure,scope);
            Ext.Ajax.request(Ext.apply({
                scope:scope,
                method:'post',
                requestexception:function(conn,response,options){
                    showMsg('failure', 'No Response From Server');
                }
            },c));
        }
     };
}();


/**
 * @class Ext.ux.TimeSpinner
 * @extends Ext.util.Observable
 * Creates a TimeSpinner control utilized by Ext.ux.form.TimeSpinnerField
 */
Ext.ux.TimeSpinner = Ext.extend(Ext.ux.Spinner, {
    incrementValue: 1,
    alternateIncrementValue: 1,
    defaultValue: new Date(),
    format : 'H:i',
    constructor: function(config){
        Ext.ux.TimeSpinner.superclass.constructor.call(this, config);
        Ext.apply(this, config);
    },
    spin: function(down, alternate){
        var v = this.field.getRawValue();
        v = Date.parseDate(v, this.format);
        var incr = (alternate == true) ? this.alternateIncrementValue : this.incrementValue;
        var dir = (down == true) ? dir = -1 : dir = 1;
        var dtconst = (alternate == true) ? Date.HOUR : Date.MINUTE;
        v = (v) ? v.add(dtconst, dir*incr) : this.defaultValue;
        v = this.fixBoundries(v);
        v = Ext.util.Format.date(v,this.format),
        this.field.setRawValue(v);
    },

    fixBoundries: function(value){
        var dt = value;
        var min = (typeof this.minValue == 'string') ? Date.parseDate(this.minValue, this.format) : this.minValue ;
        var max = (typeof this.maxValue == 'string') ? Date.parseDate(this.maxValue, this.format) : this.maxValue ;

        if(this.minValue != undefined && dt < min){
            dt = min;
        }
        if(this.maxValue != undefined && dt > max){
            dt = max;
        }

        return dt;
    }
});

//backwards compat
Ext.form.TimeSpinner = Ext.ux.TimeSpinner;


/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.ns('Ext.ux.form');

/**
 * @class Ext.ux.form.SpinnerField
 * @extends Ext.form.NumberField
 * Creates a field utilizing Ext.ux.Spinner
 * @xtype spinnerfield
 */
Ext.ux.form.TimeSpinnerField = Ext.extend(Ext.form.DateField, {
    deferHeight: true,
    autoSize: Ext.emptyFn,
    format:'H:i',
    onBlur: Ext.emptyFn,
    adjustSize: Ext.BoxComponent.prototype.adjustSize,

	constructor: function(config) {
		var spinnerConfig = Ext.copyTo({}, config, 'incrementValue,alternateIncrementValue,accelerate,defaultValue,triggerClass,splitterClass');

		var spl = this.spinner = new Ext.ux.TimeSpinner(spinnerConfig);

		var plugins = config.plugins
			? (Ext.isArray(config.plugins)
				? config.plugins.push(spl)
				: [config.plugins, spl])
			: spl;

		Ext.ux.form.TimeSpinnerField.superclass.constructor.call(this, Ext.apply(config, {plugins: plugins}));
	},

    onShow: function(){
        if (this.wrap) {
            this.wrap.dom.style.display = '';
            this.wrap.dom.style.visibility = 'visible';
        }
    },

    onHide: function(){
        this.wrap.dom.style.display = 'none';
    },

    // private
    getResizeEl: function(){
        return this.wrap;
    },

    // private
    getPositionEl: function(){
        return this.wrap;
    },

    // private
    alignErrorIcon: function(){
        if (this.wrap) {
            this.errorIcon.alignTo(this.wrap, 'tl-tr', [2, 0]);
        }
    },

    validateBlur: function(){
        return true;
    }
});

Ext.reg('timespinnerfield', Ext.ux.form.TimeSpinnerField);

//backwards compat
Ext.form.TimeSpinnerField = Ext.ux.form.TimeSpinnerField;
