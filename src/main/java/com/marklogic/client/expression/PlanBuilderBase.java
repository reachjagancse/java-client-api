/*
 * Copyright 2017 MarkLogic Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marklogic.client.expression;

import java.util.Map;

import com.marklogic.client.io.marker.JSONReadHandle;
import com.marklogic.client.type.AttributeNodeSeqExpr;
import com.marklogic.client.type.CtsQueryExpr;
import com.marklogic.client.type.ElementNodeExpr;
import com.marklogic.client.type.ItemSeqExpr;
import com.marklogic.client.type.PlanCase;
import com.marklogic.client.type.PlanFunction;
import com.marklogic.client.type.PlanGroupConcatOptionSeq;
import com.marklogic.client.type.PlanParamExpr;
import com.marklogic.client.type.PlanValueOption;
import com.marklogic.client.type.SemStoreExpr;
import com.marklogic.client.type.XmlContentNodeSeqExpr;
import com.marklogic.client.type.XsBooleanExpr;
import com.marklogic.client.type.XsLongVal;
import com.marklogic.client.type.XsQNameExpr;
import com.marklogic.client.type.XsQNameVal;

interface PlanBuilderBase {
    /**
    * Constructs a literal row set as in the SQL VALUES or SPARQL VALUES statements. 
    * @param rows  This parameter provides any number of objects in which the key is a column name string identifying the column and the value is a literal with the value of the column.
    * @return  a AccessPlan object
    */
    public PlanBuilder.AccessPlan fromLiterals(@SuppressWarnings("unchecked") Map<String,Object>... rows);

    /**
    * This function constructs an XML element with the name (which can be a string or QName), a sequence of zero or more attributes, and child content. 
    * <p>
    * Provides a client interface to a server function. See <a href="http://docs.marklogic.com/op:xml-element" target="mlserverdoc">op:xml-element</a>
    * @param name  The string or QName for the constructed element.
    * @param attributes  Any element attributes returned from <a>op:xml-attribute</a>, or <code>null</code> if no attributes.
    * @param content  A sequence or array of atomic values or an element, a comment from <a>op:xml-comment</a>, or processing instruction nodes from <a>op:xml-pi</a>.
    * @return  a ElementNodeExpr expression
    */
    public ElementNodeExpr xmlElement(XsQNameExpr name, AttributeNodeSeqExpr attributes, XmlContentNodeSeqExpr content);
    /**
    * This function returns the specified <code>value</code> if the specified <code>condition</code> is <code>true</code>. Otherwise, it returns null.
    * @param condition  A boolean expression.
    * @param value  The value expression to return if the boolean expression is <code>true</code>.
    * @return  a PlanCase object
    */
    public PlanCase when(XsBooleanExpr condition, ItemSeqExpr value);

    /**
     * Specifies a JavaScript or XQuery function installed on the server for use
     * in post-processing in a map() or reduce() operation.
     * @param functionName  the name of the function installed on the server
     * @param modulePath  the path on the server for the library module providing the function
     * @return  a PlanFunction object
     */
    public PlanFunction resolveFunction(XsQNameVal functionName, String modulePath);

    /**
     * Specifies options for aggregating the values of a column for the rows
     * belonging to each group by concatenating the values into a single string value.
     * @param separator  a string for separating the values
     * @return  a PlanGroupConcatOptionSeq object
     */
    public PlanGroupConcatOptionSeq groupConcatOptions(String separator);
    /**
     * Specifies options for aggregating the values of a column for the rows
     * belonging to each group by concatenating the values into a single string value.
     * @param option  an option controlling whether to concatenate all values including duplicates or concatenate distinct values
     * @return  a PlanGroupConcatOptionSeq object
     */
    public PlanGroupConcatOptionSeq groupConcatOptions(PlanValueOption option);
    // not a sequence constructor
    /**
     * Specifies options for aggregating the values of a column for the rows
     * belonging to each group by concatenating the values into a single string value.
     * @param separator  a string for separating the values
     * @param option  an option controlling whether to concatenate all values including duplicates or concatenate distinct values
     * @return  a PlanGroupConcatOptionSeq object
     */
    public PlanGroupConcatOptionSeq groupConcatOptions(String separator, PlanValueOption option);

    interface PlanBase {
        /**
         * Specifies a boolean primitive value to replace a placeholder parameter during this
         * execution of the plan in all expressions in which the parameter appears.
         * @param param  a placeholder parameter with as constructed by the param() method
         * @param literal   a boolean primitive value to replace the parameter
         * @return  a Plan object
         */
        public PlanBuilder.Plan bindParam(PlanParamExpr param, boolean literal);
        /**
         * Specifies a byte primitive value to replace a placeholder parameter during this
         * execution of the plan in all expressions in which the parameter appears.
         * @param param  a placeholder parameter with as constructed by the param() method
         * @param literal   a byte primitive value to replace the parameter
         * @return  a Plan object
         */
        public PlanBuilder.Plan bindParam(PlanParamExpr param, byte    literal);
        /**
         * Specifies a double primitive value to replace a placeholder parameter during this
         * execution of the plan in all expressions in which the parameter appears.
         * @param param  a placeholder parameter with as constructed by the param() method
         * @param literal   a double primitive value to replace the parameter
         * @return  a Plan object
         */
        public PlanBuilder.Plan bindParam(PlanParamExpr param, double  literal);
        /**
         * Specifies a float primitive value to replace a placeholder parameter during this
         * execution of the plan in all expressions in which the parameter appears.
         * @param param  a placeholder parameter with as constructed by the param() method
         * @param literal   a float primitive value to replace the parameter
         * @return  a Plan object
         */
        public PlanBuilder.Plan bindParam(PlanParamExpr param, float   literal);
        /**
         * Specifies an int primitive value to replace a placeholder parameter during this
         * execution of the plan in all expressions in which the parameter appears.
         * @param param  a placeholder parameter with as constructed by the param() method
         * @param literal   an int primitive value to replace the parameter
         * @return  a Plan object
         */
        public PlanBuilder.Plan bindParam(PlanParamExpr param, int     literal);
        /**
         * Specifies a long primitive value to replace a placeholder parameter during this
         * execution of the plan in all expressions in which the parameter appears.
         * @param param  a placeholder parameter with as constructed by the param() method
         * @param literal   a long primitive value to replace the parameter
         * @return  a Plan object
         */
        public PlanBuilder.Plan bindParam(PlanParamExpr param, long    literal);
        /**
         * Specifies a short primitive value to replace a placeholder parameter during this
         * execution of the plan in all expressions in which the parameter appears.
         * @param param  a placeholder parameter with as constructed by the param() method
         * @param literal   a short primitive value to replace the parameter
         * @return  a Plan object
         */
        public PlanBuilder.Plan bindParam(PlanParamExpr param, short   literal);
        /**
         * Specifies a string literal value to replace a placeholder parameter during this
         * execution of the plan in all expressions in which the parameter appears.
         * @param param  a placeholder parameter with as constructed by the param() method
         * @param literal   a string literal value to replace the parameter
         * @return  a Plan object
         */
        public PlanBuilder.Plan bindParam(PlanParamExpr param, String  literal);
    }
    interface AccessPlanBase {
    }
    interface ExportablePlanBase {
        /**
         * This method exports the plan to an AST (Abstract Sytax Tree) 
         * using the specified JSON handle.
         * @param handle  a handle for reading the AST as JSON
         * @param <T> the type of the handle for reading the AST as JSON
         * @return  the JSON handle populated with the AST
         */
        public <T extends JSONReadHandle> T export(T handle);
        /**
         * This method exports the plan to an AST (Abstract Sytax Tree) 
         * using the specified JSON handle.
         * <p>
         * The IO class must have been registered before creating the database client.
         * By default, the provided handles that implement 
         * {@link com.marklogic.client.io.marker.ContentHandle ContentHandle} are registered.
         * <p>
         * <a href="../../../../overview-summary.html#ShortcutMethods">Learn more about shortcut methods</a>
         * @param as    the IO class for reading the AST by means of JSON
         * @param <T> the type of the IO object for the AST
         * @return  an object of the IO class populated with the AST
         */
        public <T> T exportAs(Class<T> as);
    }
    interface ModifyPlanBase {
        /**
         * This method returns a subset of the rows in the result set by returning the specified number of rows.
         * @param length  The number of rows to return. 
         * @return  a ModifyPlan object
         */
        public PlanBuilder.ModifyPlan limit(long length);
        /**
         * This method returns a subset of the rows in the result set by returning the specified number of rows.
         * @param length  The number of rows to return. 
         * @return  a ModifyPlan object
         */
        public PlanBuilder.ModifyPlan limit(XsLongVal length);
        /**
         * This method returns a subset of the rows in the result set by returning the specified number of rows.
         * @param length  The number of rows to return. 
         * @return  a ModifyPlan object
         */
        public PlanBuilder.ModifyPlan limit(PlanParamExpr length);
        /**
         * This method returns a subset of the rows in the result set by skipping the number of rows specified by start and returning the remaining rows up to the number specified by the prototype.limit method.
         * @param start  The number of rows to skip. 
         * @return  a ModifyPlan object
         */
        public PlanBuilder.ModifyPlan offset(long start);
        /**
         * This method returns a subset of the rows in the result set by skipping the number of rows specified by start and returning the remaining rows up to the number specified by the prototype.limit method.
         * @param start  The number of rows to skip. 
         * @return  a ModifyPlan object
         */
        public PlanBuilder.ModifyPlan offset(XsLongVal start);
        /**
         * This method returns a subset of the rows in the result set by skipping the number of rows specified by start and returning the remaining rows up to the number specified by the prototype.limit method.
         * @param start  The number of rows to skip. 
         * @return  a ModifyPlan object
         */
        public PlanBuilder.ModifyPlan offset(PlanParamExpr start);
        /**
         * This method returns a subset of the rows in the result set by skipping the number of rows specified by start and returning the remaining rows up to the length limit. The offset for the next subset of rows is start + length. 
         * @param start  The number of rows to skip. 
         * @param length  The number of rows to return. 
         * @return  a ModifyPlan object
         */
        public PlanBuilder.ModifyPlan offsetLimit(long start, long length);
        /**
         * This method returns a subset of the rows in the result set by skipping the number of rows specified by start and returning the remaining rows up to the length limit. The offset for the next subset of rows is start + length. 
         * @param start  The number of rows to skip. 
         * @param length  The number of rows to return. 
         * @return  a ModifyPlan object
         */
        public PlanBuilder.ModifyPlan offsetLimit(XsLongVal start, XsLongVal length);
        /**
         * This method restricts the row set to rows matched by the boolean expression. Use boolean composers such as op.and and op.or to combine multiple expressions. 
         * @param condition  The boolean expression on which to match. 
         * @return  a ModifyPlan object
         */
        public PlanBuilder.ModifyPlan where(XsBooleanExpr condition);
        /**
         * This method restricts the row set to rows from the documents matched by the cts.query expression.  
         * @param condition  The cts.query expression for matching the documents.
         * @return  a ModifyPlan object
         */
        public PlanBuilder.ModifyPlan where(CtsQueryExpr condition);
        /**
         * This method adjusts the row set based on the triples for the sem.store definition, 
         * restricting the triples to the documents matched by a cts.query expression and
         * expanding the triples based on inferencing rules.  
         * @param condition  The sem.store for modifying the initial set of triples from which rows are projected.
         * @return  a ModifyPlan object
         */
        public PlanBuilder.ModifyPlan where(SemStoreExpr condition);
    }
    interface PreparePlanBase {
    }
}
