package com.github.inxilpro.intellijalpine

import com.intellij.psi.PsiElement
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlTag
import com.intellij.xml.XmlAttributeDescriptor
import com.intellij.xml.XmlElementDescriptor
import com.intellij.xml.XmlElementsGroup
import com.intellij.xml.XmlNSDescriptor

class ElementDescriptor() : XmlElementDescriptor {
    override fun getQualifiedName(): String? {
        return null
    }

    override fun getDefaultName(): String? {
        return null
    }

    override fun getElementsDescriptors(context: XmlTag?): Array<XmlElementDescriptor?>? {
        return XmlElementDescriptor.EMPTY_ARRAY
    }

    override fun getElementDescriptor(childTag: XmlTag?, contextTag: XmlTag?): XmlElementDescriptor? {
        return null
    }

    override fun getAttributesDescriptors(context: XmlTag?): Array<XmlAttributeDescriptor?>? {
        return XmlAttributeDescriptor.EMPTY
    }

    override fun getAttributeDescriptor(attribute: XmlAttribute?): XmlAttributeDescriptor? {
        return null
    }

    override fun getNSDescriptor(): XmlNSDescriptor? {
        return null
    }

    override fun getTopGroup(): XmlElementsGroup? {
        return null
    }

    override fun getContentType(): Int {
        return 0
    }

    override fun getDefaultValue(): String? {
        return null
    }

    override fun getDeclaration(): PsiElement? {
        return null
    }

    override fun getName(context: PsiElement?): String? {
        return null
    }

    override fun getName(): String? {
        return null
    }

    override fun init(element: PsiElement?) {
    }

    override fun getAttributeDescriptor(attributeName: String?, context: XmlTag?): XmlAttributeDescriptor? {
        println(attributeName)
        println(context)
        if(attributeName == null || context == null) return null
        val info = AttributeInfo(attributeName)

        if (info.isAlpine()) {
            return AlpineAttributeDescriptor(attributeName, context)
        }
        return null;
    }
}