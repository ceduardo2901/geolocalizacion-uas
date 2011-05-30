<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:demo="http://java.sun.com/jsf/composite/demo"
	xmlns:rich="http://richfaces.org/rich">
<f:view>
	<h:head>
	</h:head>
	<body>

            <f:view>

                  <a4j:form>

                        <rich:panel header="RichFaces Greeter" style="width: 315px">

                              <h:outputText value="Your name: " />

                              <h:inputText value="#{user.name}" >

                                    <f:validateLength minimum="1" maximum="30" />

                              </h:inputText>

                              

                              <a4j:commandButton value="Get greeting" reRender="greeting" />

                              

                              <h:panelGroup id="greeting" >

                                    <h:outputText value="Hello, " rendered="#{not empty user.name}" />

                                    <h:outputText value="#{user.name}" />

                                    <h:outputText value="!" rendered="#{not empty user.name}" />

                              </h:panelGroup>

                        </rich:panel>

                  </a4j:form>

            </f:view>

      </body>
</f:view>
</html>