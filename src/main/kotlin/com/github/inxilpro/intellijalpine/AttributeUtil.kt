package com.github.inxilpro.intellijalpine

import com.intellij.psi.html.HtmlTag
import com.intellij.psi.impl.source.html.dtd.HtmlElementDescriptorImpl
import com.intellij.psi.impl.source.html.dtd.HtmlNSDescriptorImpl
import com.intellij.psi.xml.XmlTag
import com.intellij.xml.XmlAttributeDescriptor

object AttributeUtil {
    private val validAttributes = mutableMapOf<String, Array<String>>()

    val xmlPrefixes = arrayOf(
        "x-on",
        "x-bind",
        "x-transition"
    )

    val directives = arrayOf(
        "x-data",
        "x-init",
        "x-show",
        "x-model",
        "x-text",
        "x-html",
        "x-ref",
        "x-if",
        "x-for",
        "x-transition:enter",
        "x-transition:enter-start",
        "x-transition:enter-end",
        "x-transition:leave",
        "x-transition:leave-start",
        "x-transition:leave-end",
        "x-spread",
        "x-cloak"
    )

    val eventPrefixes = arrayOf(
        "@",
        "x-on:"
    )

    val bindPrefixes = arrayOf(
        ":",
        "x-bind:"
    )

    fun isXmlPrefix(prefix: String): Boolean {
        return xmlPrefixes.contains(prefix)
    }

    fun getValidAttributes(xmlTag: HtmlTag): Array<String> {
        return validAttributes.getOrPut(xmlTag.name, { buildValidAttributes(xmlTag) })
    }

    fun getValidAttributesWithInfo(xmlTag: HtmlTag): Array<AttributeInfo> {
        return getValidAttributes(xmlTag)
            .map { AttributeInfo(it) }
            .toTypedArray()
    }

    fun isEvent(attribute: String): Boolean {
        for (prefix in eventPrefixes) {
            if (attribute.startsWith(prefix)) {
                return true
            }
        }

        return false
    }

    private fun buildValidAttributes(htmlTag: HtmlTag): Array<String> {
        val descriptors = mutableListOf<String>()

        for (directive in directives) {
            if (htmlTag.name != "template" && (directive == "x-if" || directive == "x-for")) {
                continue
            }

            descriptors.add(directive)
        }

        for (descriptor in getDefaultHtmlAttributes(htmlTag)) {
            if (descriptor.name.startsWith("on")) {
                val event = descriptor.name.substring(2)
                for (prefix in eventPrefixes) {
                    descriptors.add(prefix + event)
                }
            } else {
                for (prefix in bindPrefixes) {
                    descriptors.add(prefix + descriptor.name)
                }
            }
        }

        return descriptors.toTypedArray()
    }

    private fun getDefaultHtmlAttributes(xmlTag: XmlTag): Array<out XmlAttributeDescriptor> {
        val tagDescriptor = xmlTag.descriptor as? HtmlElementDescriptorImpl
        val descriptor = tagDescriptor ?: HtmlNSDescriptorImpl.guessTagForCommonAttributes(xmlTag)

        return (descriptor as? HtmlElementDescriptorImpl)?.getDefaultAttributeDescriptors(xmlTag) ?: emptyArray()
    }
}
