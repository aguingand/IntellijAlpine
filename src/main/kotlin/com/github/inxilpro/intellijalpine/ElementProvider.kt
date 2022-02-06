package com.github.inxilpro.intellijalpine

import com.intellij.html.impl.DelegatingRelaxedHtmlElementDescriptor
import com.intellij.psi.impl.source.html.dtd.HtmlElementDescriptorImpl
import com.intellij.psi.impl.source.xml.XmlElementDescriptorProvider
import com.intellij.psi.xml.XmlTag
import com.intellij.xml.XmlElementDescriptor
import com.intellij.xml.impl.schema.AnyXmlElementDescriptor
import com.intellij.xml.impl.schema.NullElementDescriptor

class ElementProvider: XmlElementDescriptorProvider {
    override fun getDescriptor(tag: XmlTag?): XmlElementDescriptor? {
        if(tag == null) return null
        println(tag.containingFile.name);
        println(tag.descriptor);
        if(
//                tag.containingFile.name.endsWith(".blade.php") &&
                tag.name.startsWith("x-")) {
            return HtmlElementDescriptorImpl(
                    AnyXmlElementDescriptor(null, null),
                    true,
                    false
            );
        }
        return null
    }
}