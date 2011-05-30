<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:a4j="http://richfaces.org/a4j">
<f:view>
	<h:head>
	</h:head>
	<h:body>
		<a4j:outputPanel ajaxRendered="true">
			<h:outputText value="#{commandBean.date}" />
		</a4j:outputPanel>
		<h:outputText value="Name:" />
		<h:form>
			<h:inputText value="#{commandBean.name}" />

			<h:panelGrid>
				<a4j:commandButton action="#{commandBean.submit}" render="out"
					value="Say Hello" />
				<a4j:commandButton action="#{commandBean.submit}" render="out"
					value="Say Hello with limitRender" limitRender="true" />
				<a4j:commandButton action="#{commandBean.submit}" render="out"
					execute="@this">
					<h:outputText value="Say Hello with execute=@this" />
				</a4j:commandButton>
				<a4j:commandButton action="#{commandBean.reset}" render="out"
					value="Reset" />
				<a4j:commandButton value="Test AjaxBehavior">
					<f:ajax event="action" execute="@form" render=":out"
						listener="#{commandBean.listener}" />
				</a4j:commandButton>
			</h:panelGrid>
		</h:form>
		<br />
		<h:panelGroup id="out">
			<h:outputText value="#{commandBean.name}" />
			<h:outputText value="!" rendered="#{not empty commandBean.name}" />
		</h:panelGroup>
		<br />
		<hr />
		RF-9146: request grouping id not set:
		<br />
		<h:panelGroup id="out1">
			<h:outputText value="#{commandBean.name}" />
		</h:panelGroup>
		<h:form>
			<a4j:queue requestDelay="5000" />
			<a4j:commandButton value="Update 1" render="out1">
				<a4j:param name="v1" value="Update 1" assignTo="#{commandBean.name}" />
			</a4j:commandButton>
			<a4j:commandButton value="Update 2" render="out1">
				<a4j:param name="v1" value="Update 2" assignTo="#{commandBean.name}" />
			</a4j:commandButton>
			<hr />
			<a4j:log level="debug" style="border: solid red 1px" styleClass="log"
				mode="popup" />
		</h:form>
	</h:body>
</f:view>
</html>