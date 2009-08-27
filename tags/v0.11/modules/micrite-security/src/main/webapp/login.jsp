<%--
/* ===========================================================
 * $Id$
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>Micrite</title>
<link rel="stylesheet" type="text/css"  href="js-lib/ext-js/resources/css/ext-all.css" />
<script type="text/javascript" src="js-lib/ext-js/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="js-lib/ext-js/ext-all.js"></script>
<style>
.micrite {
    padding-top: 4px;
    padding-right: 550px;
}
</style>
<script type="text/javascript">
Ext.SSL_SECURE_URL = "js-lib/ext-js/resources/images/default/s.gif";
Ext.BLANK_IMAGE_URL = "js-lib/ext-js/resources/images/default/s.gif";

/**
 * Ext.ux.form.IconComboBox Extension Class for Ext 2.x Library
 *
 * @author  Ing. Jozef Sakalos
 * @version $Id$
 *
 * @license Ext.ux.form.IconComboBox is licensed under the terms of
 * the Open Source LGPL 3.0 license.  Commercial use is permitted to the extent
 * that the code/component(s) do NOT become part of another Open Source or Commercially
 * licensed development library or toolkit without explicit permission.
 * 
 * License details: http://www.gnu.org/licenses/lgpl.html
 */

/**
 * @class Ext.ux.form.IconComboBox
 * @extends Ext.form.ComboBox
 */

Ext.namespace('Ext.ux.form');

Ext.ux.form.IconCombo = Ext
        .extend(
                Ext.form.ComboBox,
                {
                    initComponent : function() {
                        var css = '.ux-icon-combo-icon {background-repeat: no-repeat;background-position: 0 50%;width: 18px;height: 14px;}'
                                + '.ux-icon-combo-input {padding-left: 25px;}'
                                + '.x-form-field-wrap .ux-icon-combo-icon {top: 4px;left: 5px;}'
                                + '.ux-icon-combo-item {background-repeat: no-repeat ! important;background-position: 3px 50% ! important;padding-left: 24px ! important;}'
                                + ".ux-flag-zh_cn {background-image:url(security/images/framework/cn_zh.png) !important;}"
                                + ".ux-flag-us {background-image:url(security/images/framework/en_us.png) !important;}";

                        Ext.util.CSS.createStyleSheet(css, this._cssId);

                        Ext
                                .apply(
                                        this,
                                        {
                                            tpl : '<tpl for=".">'
                                                    + '<div class="x-combo-list-item ux-icon-combo-item '
                                                    + '{' + this.iconClsField
                                                    + '}">' + '{'
                                                    + this.displayField + '}'
                                                    + '</div></tpl>'
                                        });

                        // call parent initComponent
                        Ext.ux.form.IconCombo.superclass.initComponent.apply(
                                this, arguments);

                    } // eo function initComponent

                    ,
                    onRender : function(ct, position) {
                        // call parent onRender
                        Ext.ux.form.IconCombo.superclass.onRender.apply(this,
                                arguments);

                        // adjust styles
                        this.wrap.applyStyles( {
                            position : 'relative'
                        });
                        this.el.addClass('ux-icon-combo-input');

                        // add div for icon
                        this.icon = Ext.DomHelper.append(this.el
                                .up('div.x-form-field-wrap'), {
                            tag : 'div',
                            style : 'position:absolute'
                        });
                    } // eo function onRender

                    ,
                    afterRender : function() {
                        Ext.ux.form.IconCombo.superclass.afterRender.apply(
                                this, arguments);
                        if (undefined !== this.value) {
                            this.setValue(this.value);
                        }
                    } // eo function afterRender
                    ,
                    setIconCls : function() {
                        var rec = this.store.query(this.valueField,
                                this.getValue()).itemAt(0);
                        if (rec && this.icon) {
                            this.icon.className = 'ux-icon-combo-icon ' + rec
                                    .get(this.iconClsField);
                        }
                    } // eo function setIconCls

                    ,
                    setValue : function(value) {
                        Ext.ux.form.IconCombo.superclass.setValue.call(this,
                                value);
                        this.setIconCls();
                    } // eo function setValue

                    ,
                    _cssId : 'ux-IconCombo-css'

                });

// register xtype
Ext.reg('iconcombo', Ext.ux.form.IconCombo);

/**
 * @Description Ext.ux.LoginWindow for ExtJS 2.x and 3.x
 * @author  Wemerson Januario (Brazil - Goiânia)
 * @author  Albert Varaksin
 * @author  Sumit Madan
 * @license LGPLv3 http://www.opensource.org/licenses/lgpl-3.0.html
 * @version 1.0, 09/05/2009
 */
Ext.namespace('Ext.ux');
Ext.namespace('micrite.security.framework');
/**
 * Construtor da janela de login
 *
 * @param {Object} config
 * @extends {Ext.util.Observable}
 */

Ext.ux.LoginWindow = function(config) {
    Ext.apply(this, config);
    var css = "#login-logo .x-plain-body {background:#f9f9f9 url('"
            + this.basePath + "/" + this.winBanner + "') no-repeat;}"
            + "#login-form  {background: " + this.formBgcolor + " none;}"
            + ".ux-auth-header-icon {background: url('" + this.basePath
            + "/locked.gif') 0 4px no-repeat !important;}"
            + ".ux-auth-form {padding:10px;}"
            + ".ux-auth-login {background-image: url('" + this.basePath
            + "/key.gif') !important}"
            + ".ux-auth-close {background-image: url('" + this.basePath
            + "/close.gif') !important}";

    Ext.util.CSS.createStyleSheet(css, this._cssId);
    // Eventos do LoginWindow
    this.addEvents( {
        'show' : true,
        'reset' : true,
        'submit' : true
    });
    Ext.ux.LoginWindow.superclass.constructor.call(this, config);

    //Painel topo (Logotipo do sistema)
    this._logoPanel = new Ext.Panel( {
        baseCls : 'x-plain',
        id : 'login-logo',
        region : 'center'
    });
    // Seta id para o elementos
    this.usernameId = Ext.id();
    this.passwordId = Ext.id();
    this.languageId = Ext.id();
    this._loginButtonId = Ext.id();
    this._resetButtonId = Ext.id();
    this._formPanel = new Ext.form.FormPanel( {
        region : 'south',
        border : false,
        bodyStyle : "padding: 5px;",
        baseCls : 'x-plain',
        id : 'login-form',
        waitMsgTarget : true,
        labelWidth : 80,
        defaults : {
            width : 300
        },
        baseParams : {
            task : 'login'
        },
        listeners : {
            'actioncomplete' : {
                fn : this.onSuccess,
                scope : this
            },
            'actionfailed' : {
                fn : this.onFailure,
                scope : this
            }
        },
        height : 110,
        items : [
                {
                    xtype : 'textfield',
                    id : this.usernameId,
                    name : this.usernameField,
                    fieldLabel : this.usernameLabel,
                    vtype : this.usernameVtype,
                    validateOnBlur : false,
                    allowBlank : false
                },
                {
                    xtype : 'textfield',
                    inputType : 'password',
                    id : this.passwordId,
                    name : this.passwordField,
                    fieldLabel : this.passwordLabel,
                    vtype : this.passwordVtype,
                    validateOnBlur : false,
                    allowBlank : false
                },
                {
                    xtype : 'iconcombo',
                    id : this.languageId,
                    hiddenName : this.languageField,
                    fieldLabel : this.languageLabel,
                    store : new Ext.data.SimpleStore( {
                        fields : [ 'languageCode', 'languageName',
                                'countryFlag' ],
                        data : [
                                [ 'zh_CN', '简体中文',
                                        'ux-flag-zh_cn' ],
                                [ 'en', 'English',
                                        'ux-flag-us' ] ]
                    }),
                    valueField : 'languageCode',
                    value : this.defaultLanguage,
                    displayField : 'languageName',
                    iconClsField : 'countryFlag',
                    triggerAction : 'all',
                    editable : false,
                    mode : 'local'
                } ]
    });
    Ext.getCmp(this.languageId).on('select', function() {
        this.defaultLanguage = Ext.getCmp(this.languageId).getValue(); //var lang = this.defaultLanguage;   
            this.setlanguage();
        }, this);

    // Botões padrões
    var buttons = [ {
        id : this._loginButtonId,
        text : this.loginButton,
        handler : this.submit,
        scale : 'medium',
        scope : this
    } ];
    var keys = [ {
        key : [ 10, 13 ],
        //Tecla ENTER
        handler : this.submit,
        scope : this
    } ];

    //Testa se o botão que reseta o formulário existe
    if (typeof this.resetButton == 'string') {
        buttons.push( {
            id : this._resetButtonId,
            text : this.resetButton,
            handler : this.reset,
            scale : 'medium',
            scope : this
        });
        keys.push( {
            key : [ 27 ],
            //Tecla ESC
            handler : this.reset,
            scope : this
        });
    }
    //Cria a janela principal de login
    this._window = new Ext.Window( {
        width : 429,
        height : 280,
        closable : false,
        resizable : false,
        draggable : true,
        modal : this.modal,
        iconCls : 'ux-auth-header-icon',
        title : this.title,
        layout : 'border',
        bodyStyle : 'padding:5px;',
        buttons : buttons,
        buttonAlign : 'center',
        keys : keys,
        plain : false,
        items : [ this._logoPanel, this._formPanel ]
    });

    //Seta foco no campo username quando a janela principal é exibida
    // Dispara o evento "show"
    this._window.on('show', function() {
        this.setlanguage();
       
        this.fireEvent('show', this);
    }, this);
};

// Extende a classe Ext.util.Observable
Ext.extend(Ext.ux.LoginWindow, Ext.util.Observable, {
    /**
     * Título da janela principal
     *
     * @type {String}
     */
    title : '',
    /**
     * Título da janela de recuperação de senha
     *
     * @type {String}
     */
    Passwordtitle : '',
    /**
     * Mensagem de espera ao enviar os dados
     *
     * @type {String}
     */
    waitMessage : '',
    /**
     * Texto do botão de login
     *
     * @type {String}
     */
    //loginButton : Ext.lang.us.login,
    loginButton : '',
    /**
     * Texto do botão de recuperação de senha
     *
     * @type {String}
     */
    resetButton : '',
    /**
     * Título do campo usuário
     *
     * @type {String}
     */
    usernameLabel : '',
    /**
     * Nome do campo usuário
     *
     * @type {String}
     */
    usernameField : 'j_username',
    /**
     * Validação do campo usuário
     *
     * @type {String}
     */
    usernameVtype : 'alphanum',
    passwordLabel : '',
    /**
     * Nome do campo senha
     *
     * @type {String}
     */
    passwordField : 'j_password',
    /**
     * Validação do campo senha
     *
     * @type {String}
     */
    passwordVtype : 'alphanum',
    /**
     * Nome do combo idioma
     *
     * @type {String}
     */
    languageField : 'lang',
    /**
     * Título do iconcombobox idioma
     *
     * @type {String}
     */
    languageLabel : '',
    /**
     * Url de requisição de login
     *
     * @type {String}
     */
    url : '',
    /**
     * Url de destino caso login seja efetivado
     *
     * @type {String}
     */
    locationUrl : '',
    /**
     * Diretório das imagens
     *
     * @type {String}
     */
    basePath : 'img',
    /**
     * Logotipo do sistema (Banner)
     *
     * @type {String}
     */
    winBanner : '',
    /**
     * Cor de fundo do formulário
     *
     * @type {String}
     */
    formBgcolor : '',
    /**
     * Método de envio do formulário
     *
     * @type {String}
     */
    method : 'post',
    /**
     * Abrir janela modal
     *
     * @type {Bool}
     */
    modal : false,
    /**
     * Identificador do CSS
     *
     * @type {String}
     */
    _cssId : 'ux-LoginWindow-css',
    /**
     * Painel topo (Logotipo do sistema)
     *
     * @type {Ext.Panel}
     */
    _logoPanel : null,
    /**
     * Painel do formulário
     *
     * @type {Ext.form.FormPanel}
     */
    _formPanel : null,
    /**
     * Objeto da janela principal
     *
     * @type {Ext.Window}
     */
    _window : null,
    /**
     * Objeto da janela de recuperação de senha
     *
     * @type {Ext.Window}
     */

     errorMsg : null,
     
    show : function(el) {
        this._window.show(el);
        (function(){
            Ext.getCmp(this.usernameId).focus(true,true);
       }).defer(1000, this);
    },

    /**
     * Limpa o formulário
     */
    reset : function() {
        if (this.fireEvent('reset', this)) {
            Ext.getDom(this.usernameId).value = '';
            Ext.getDom(this.passwordId).value = '';
        }
    },
    /**
     * Idioma padrão do formulário
     */
    defaultLanguage : 'en',
    /**
     * Seleciona o idioma
     */
    setlanguage : function() {
        Ext.override(Ext.form.Field, {
            setFieldLabel : function(text) {
                if (this.rendered) {
                    this.el.up('.x-form-item', 10, true).child(
                            '.x-form-item-label').update(text);
                } else {
                    this.fieldLabel = text;
                }
            }
        });
        if (this.defaultLanguage == 'zh_CN') {
            this._window.setTitle('认证');
            Ext.getCmp(this._loginButtonId).setText('登录');
            Ext.getCmp(this._resetButtonId).setText('清除');
            Ext.getCmp(this.usernameId).setFieldLabel('用户名:');
            Ext.getCmp(this.passwordId).setFieldLabel('密码:');
            Ext.getCmp(this.languageId).setFieldLabel('语言:');
            this.errorMsg = '用户名或者密码不正确.'
            this.waitMessage = '发送数据中...';
        } else if (this.defaultLanguage == 'en') {
            this._window.setTitle('Authentication');
            Ext.getCmp(this._loginButtonId).setText('Login');
            Ext.getCmp(this._resetButtonId).setText('Clear');
            Ext.getCmp(this.usernameId).setFieldLabel('Username:');
            Ext.getCmp(this.passwordId).setFieldLabel('Password:');
            Ext.getCmp(this.languageId).setFieldLabel('Language:');
            this.errorMsg = 'The username or password you entered is incorrect.'
            this.waitMessage = 'Sending data...';
        }
    },

    /**
     * Envia a requisição de login
     */
    submit : function() {
        var form = this._formPanel.getForm();

        if (form.isValid()) {
            if (this.fireEvent('submit', this, form.getValues())) {
                form.submit( {
                    url : this.url,
                    method : this.method,
                    waitMsg : this.waitMessage,
                    success : this.onSuccess,
                    failure : this.onFailure,
                    scope : this
                });
            }
        }
    },

    /**
     * Se receber sucesso
     *
     * @param {Ext.form.BasicForm} form
     * @param {Ext.form.Action} action
     */
    onSuccess : function(form, action) {
        if (action && action.result) {
            var lang = '?request_locale=' + Ext.getCmp(this.languageId).getValue();
            window.location = this.locationUrl+lang;
        }
    },
    /**
     * Se receber falha
     *
     * @param {Ext.form.BasicForm} form
     * @param {Ext.form.Action} action
     */
    onFailure : function(form, action) { // enable buttons
        Ext.getCmp(this._loginButtonId).enable();
        if (Ext.getCmp(this._resetButtonId)) {
            Ext.getCmp(this._resetButtonId).enable();
        }
        Ext.getCmp(this.usernameId).focus(true,true);
        //var res = action.result.errorMsg.reason;
        var html = '<div style="text-align:center;padding-top:10px;color:red;">'+this.errorMsg+'</joker>';
        Ext.select('.x-form-clear-left').each(function(o,g,i){
            if (i==2)
            o.insertSibling(html,'after')
        })
    }
});

Ext.onReady( function() {
    Ext.QuickTips.init();
    var LoginWindow = new Ext.ux.LoginWindow( {
        modal : true,
        //formBgcolor:'#f0edce',
        defaultLanguage : 'en',
        basePath : 'security/images/framework/',
        winBanner : 'login.jpg',
        url : 'j_spring_security_check',
        locationUrl : 'main.action'
    });
    LoginWindow.show();
});
</script>
</head>
<body>
</body>
</html>