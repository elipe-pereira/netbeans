/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.lexer.gen;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import org.netbeans.modules.lexer.gen.antlr.AntlrLanguageDescriptionGenerator;

/**
 * Generate language description xml file from xxxTokenTypes class
 * generated by Antlr.
 * <BR>If the file already exists the content is appended to the end
 * of the file.
 *
 * @author Miloslav Metelka
 */
public class AntlrGenerateLanguageDescription extends GenerateLanguageDescription {

    protected String generate(String tokenTypesClassName) throws ClassNotFoundException {
        
        AntlrLanguageDescriptionGenerator generator
            = new AntlrLanguageDescriptionGenerator();

        return generator.generate(tokenTypesClassName);
    }
            
}
