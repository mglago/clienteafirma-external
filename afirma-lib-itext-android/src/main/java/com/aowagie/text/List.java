/*
 * $Id: List.java 3373 2008-05-12 16:21:24Z xlv $
 *
 * Copyright 1999, 2000, 2001, 2002 by Bruno Lowagie.
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the License.
 *
 * The Original Code is 'iText, a free JAVA-PDF library'.
 *
 * The Initial Developer of the Original Code is Bruno Lowagie. Portions created by
 * the Initial Developer are Copyright (C) 1999, 2000, 2001, 2002 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000, 2001, 2002 by Paulo Soares. All Rights Reserved.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * LGPL license (the "GNU LIBRARY GENERAL PUBLIC LICENSE"), in which case the
 * provisions of LGPL are applicable instead of those above.  If you wish to
 * allow use of your version of this file only under the terms of the LGPL
 * License and not to allow others to use your version of this file under
 * the MPL, indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by the LGPL.
 * If you do not delete the provisions above, a recipient may use your version
 * of this file under either the MPL or the GNU LIBRARY GENERAL PUBLIC LICENSE.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the MPL as stated above or under the terms of the GNU
 * Library General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * http://www.lowagie.com/iText/
 */

package com.aowagie.text;

import java.util.ArrayList;
import java.util.Iterator;

import com.aowagie.text.factories.RomanAlphabetFactory;

/**
 * A <CODE>List</CODE> contains several <CODE>ListItem</CODE>s.
 * <P>
 * <B>Example 1:</B>
 * <BLOCKQUOTE><PRE>
 * <STRONG>List list = new List(true, 20);</STRONG>
 * <STRONG>list.add(new ListItem("First line"));</STRONG>
 * <STRONG>list.add(new ListItem("The second line is longer to see what happens once the end of the line is reached. Will it start on a new line?"));</STRONG>
 * <STRONG>list.add(new ListItem("Third line"));</STRONG>
 * </PRE></BLOCKQUOTE>
 *
 * The result of this code looks like this:
 *	<OL>
 *		<LI>
 *			First line
 *		</LI>
 *		<LI>
 *			The second line is longer to see what happens once the end of the line is reached. Will it start on a new line?
 *		</LI>
 *		<LI>
 *			Third line
 *		</LI>
 *	</OL>
 *
 * <B>Example 2:</B>
 * <BLOCKQUOTE><PRE>
 * <STRONG>List overview = new List(false, 10);</STRONG>
 * <STRONG>overview.add(new ListItem("This is an item"));</STRONG>
 * <STRONG>overview.add("This is another item");</STRONG>
 * </PRE></BLOCKQUOTE>
 *
 * The result of this code looks like this:
 *	<UL>
 *		<LI>
 *			This is an item
 *		</LI>
 *		<LI>
 *			This is another item
 *		</LI>
 *	</UL>
 *
 * @see		Element
 * @see		ListItem
 */

public class List implements TextElementArray {

    // member variables

	/** This is the <CODE>ArrayList</CODE> containing the different <CODE>ListItem</CODE>s. */
	private final ArrayList list = new ArrayList();

    /** Indicates if the list has to be numbered. */
    private boolean numbered = false;
    /** Indicates if the listsymbols are numerical or alphabetical. */
    private boolean lettered = false;
    /** Indicates if the listsymbols are lowercase or uppercase. */
    private boolean lowercase = false;
    /** Indicates if the indentation has to be set automatically. */
    private boolean autoindent = false;
    /** Indicates if the indentation of all the items has to be aligned. */
    private boolean alignindent = false;

    /** This variable indicates the first number of a numbered list. */
    private int first = 1;
    /** This is the listsymbol of a list that is not numbered. */
    private Chunk symbol = new Chunk("- ");
    /**
     * In case you are using numbered/lettered lists, this String is added before the number/letter.
     * @since	iText 2.1.1
     */
    private String preSymbol = "";
    /**
     * In case you are using numbered/lettered lists, this String is added after the number/letter.
     * @since	iText 2.1.1
     */
    private String postSymbol = ". ";

    /** The indentation of this list on the left side. */
    private float indentationLeft = 0;
    /** The indentation of this list on the right side. */
    private float indentationRight = 0;
    /** The indentation of the listitems. */
    private float symbolIndent = 0;

    // constructors

    /** Constructs a <CODE>List</CODE>. */
    public List() {
        this(false, false);
    }

    /**
     * Constructs a <CODE>List</CODE>.
     * @param	numbered		a boolean
     * @param lettered has the list to be 'numbered' with letters
     */
    private List(final boolean numbered, final boolean lettered) {
    	this.numbered = numbered;
        this.lettered = lettered;
        this.autoindent = true;
        this.alignindent = true;
    }

    /**
     * Creates a list
     * @param numbered has the list to be numbered?
     * @param lettered has the list to be 'numbered' with letters
     * @param symbolIndent the indentation of the symbol
     */
    private List(final boolean numbered, final boolean lettered, final float symbolIndent) {
        this.numbered = numbered;
        this.lettered = lettered;
        this.symbolIndent = symbolIndent;
    }

    // implementation of the Element-methods



    /**
     * Gets the type of the text element.
     *
     * @return	a type
     */
    @Override
	public int type() {
        return Element.LIST;
    }

    /**
     * Gets all the chunks in this element.
     *
     * @return	an <CODE>ArrayList</CODE>
     */
    @Override
	public ArrayList getChunks() {
        final ArrayList tmp = new ArrayList();
        for (final Iterator i = this.list.iterator(); i.hasNext(); ) {
            tmp.addAll(((Element) i.next()).getChunks());
        }
        return tmp;
    }

    // methods to set the membervariables

    /**
     * Adds an <CODE>Object</CODE> to the <CODE>List</CODE>.
     *
     * @param	o		the object to add.
     * @return true if adding the object succeeded
     */
    @Override
	public boolean add(final Object o) {
        if (o instanceof ListItem) {
            final ListItem item = (ListItem) o;
            if (this.numbered || this.lettered) {
                final Chunk chunk = new Chunk(this.preSymbol, this.symbol.getFont());
                final int index = this.first + this.list.size();
                if ( this.lettered ) {
					chunk.append(RomanAlphabetFactory.getString(index, this.lowercase));
				} else {
					chunk.append(String.valueOf(index));
				}
                chunk.append(this.postSymbol);
                item.setListSymbol(chunk);
            }
            else {
                item.setListSymbol(this.symbol);
            }
            item.setIndentationLeft(this.symbolIndent, this.autoindent);
            item.setIndentationRight(0);
            return this.list.add(item);
        }
        else if (o instanceof List) {
            final List nested = (List) o;
            nested.setIndentationLeft(nested.getIndentationLeft() + this.symbolIndent);
            this.first--;
            return this.list.add(nested);
        }
        else if (o instanceof String) {
            return this.add(new ListItem((String) o));
        }
        return false;
    }

    // extra methods



    // setters

	/**
	 * @param numbered the numbered to set
	 */
	public void setNumbered(final boolean numbered) {
		this.numbered = numbered;
	}

	/**
	 * @param lettered the lettered to set
	 */
	public void setLettered(final boolean lettered) {
		this.lettered = lettered;
	}

	/**
	 * @param uppercase the uppercase to set
	 */
	public void setLowercase(final boolean uppercase) {
		this.lowercase = uppercase;
	}

	/**
	 * @param autoindent the autoindent to set
	 */
	public void setAutoindent(final boolean autoindent) {
		this.autoindent = autoindent;
	}
	/**
	 * @param alignindent the alignindent to set
	 */
	public void setAlignindent(final boolean alignindent) {
		this.alignindent = alignindent;
	}

    /**
     * Sets the number that has to come first in the list.
     *
     * @param	first		a number
     */
    public void setFirst(final int first) {
        this.first = first;
    }

    /**
     * Sets the listsymbol.
     *
     * @param	symbol		a <CODE>Chunk</CODE>
     */
    public void setListSymbol(final Chunk symbol) {
        this.symbol = symbol;
    }

    /**
     * Sets the listsymbol.
     * <P>
     * This is a shortcut for <CODE>setListSymbol(Chunk symbol)</CODE>.
     *
     * @param	symbol		a <CODE>String</CODE>
     */
    public void setListSymbol(final String symbol) {
        this.symbol = new Chunk(symbol);
    }

    /**
     * Sets the indentation of this paragraph on the left side.
     *
     * @param	indentation		the new indentation
     */
    public void setIndentationLeft(final float indentation) {
        this.indentationLeft = indentation;
    }

    /**
     * Sets the indentation of this paragraph on the right side.
     *
     * @param	indentation		the new indentation
     */
    public void setIndentationRight(final float indentation) {
        this.indentationRight = indentation;
    }

	/**
	 * @param symbolIndent the symbolIndent to set
	 */
	public void setSymbolIndent(final float symbolIndent) {
		this.symbolIndent = symbolIndent;
	}

    // methods to retrieve information

    /**
     * Gets all the items in the list.
     *
     * @return	an <CODE>ArrayList</CODE> containing <CODE>ListItem</CODE>s.
     */
    public ArrayList getItems() {
        return this.list;
    }

    /**
     * Returns <CODE>true</CODE> if the list is empty.
     *
     * @return <CODE>true</CODE> if the list is empty
     */
    public boolean isEmpty() {
    	return this.list.isEmpty();
    }

    /**
     * Gets the leading of the first listitem.
     *
     * @return	a <CODE>leading</CODE>
     */
    public float getTotalLeading() {
        if (this.list.size() < 1) {
            return -1;
        }
        final ListItem item = (ListItem) this.list.get(0);
        return item.getTotalLeading();
    }

    // getters

    /**
     * Checks if the list is numbered.
     * @return	<CODE>true</CODE> if the list is numbered, <CODE>false</CODE> otherwise.
     */

    public boolean isNumbered() {
        return this.numbered;
    }

    /**
     * Checks if the list is lettered.
     * @return  <CODE>true</CODE> if the list is lettered, <CODE>false</CODE> otherwise.
     */
    public boolean isLettered() {
        return this.lettered;
    }

    /**
     * Checks if the list lettering is lowercase.
     * @return  <CODE>true</CODE> if it is lowercase, <CODE>false</CODE> otherwise.
     */
    public boolean isLowercase() {
        return this.lowercase;
    }

    /**
     * Checks if the indentation of list items is done automatically.
	 * @return the autoindent
	 */
	public boolean isAutoindent() {
		return this.autoindent;
	}

	/**
	 * Checks if all the listitems should be aligned.
	 * @return the alignindent
	 */
	public boolean isAlignindent() {
		return this.alignindent;
	}

	/**
     * Gets the first number        .
     * @return a number
	 */
	public int getFirst() {
		return this.first;
	}

	/**
     * Gets the Chunk containing the symbol.
     * @return a Chunk with a symbol
	 */
	public Chunk getSymbol() {
		return this.symbol;
	}

	/**
     * Gets the indentation of this paragraph on the left side.
     * @return	the indentation
	 */
	public float getIndentationLeft() {
		return this.indentationLeft;
	}

	/**
     * Gets the indentation of this paragraph on the right side.
     * @return	the indentation
	 */
	public float getIndentationRight() {
		return this.indentationRight;
	}

	/**
     * Gets the symbol indentation.
     * @return the symbol indentation
	 */
	public float getSymbolIndent() {
		return this.symbolIndent;
	}
	/**
	 * @see com.aowagie.text.Element#isContent()
	 * @since	iText 2.0.8
	 */
	@Override
	public boolean isContent() {
		return true;
	}

	/**
	 * @see com.aowagie.text.Element#isNestable()
	 * @since	iText 2.0.8
	 */
	@Override
	public boolean isNestable() {
		return true;
	}

	/**
	 * Returns the String that is after a number or letter in the list symbol.
	 * @return	the String that is after a number or letter in the list symbol
	 * @since	iText 2.1.1
	 */
	public String getPostSymbol() {
		return this.postSymbol;
	}

	/**
	 * Sets the String that has to be added after a number or letter in the list symbol.
	 * @since	iText 2.1.1
	 * @param	postSymbol the String that has to be added after a number or letter in the list symbol.
	 */
	public void setPostSymbol(final String postSymbol) {
		this.postSymbol = postSymbol;
	}

	/**
	 * Returns the String that is before a number or letter in the list symbol.
	 * @return	the String that is before a number or letter in the list symbol
	 * @since	iText 2.1.1
	 */
	public String getPreSymbol() {
		return this.preSymbol;
	}

	/**
	 * Sets the String that has to be added before a number or letter in the list symbol.
	 * @since	iText 2.1.1
	 * @param	preSymbol the String that has to be added before a number or letter in the list symbol.
	 */
	public void setPreSymbol(final String preSymbol) {
		this.preSymbol = preSymbol;
	}

}