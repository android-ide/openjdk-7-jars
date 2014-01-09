/*
 * Copyright (c) 2014, appfour GmbH. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details.
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.appfour.codestripper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

public class Main {
	private static boolean INCLUDE_PACKAGE_PRIVATE_CLASSES = true;
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("Usage: java " + Main.class.getCanonicalName()
					+ " <source dir> <dest dir>");
			System.exit(1);
		}
		for (File file : new File(args[0]).listFiles()) {
			if (file.getName().toLowerCase(Locale.US).endsWith(".jar")) {
				System.err.println("Processing JAR: "+file.getPath());
				ZipInputStream zipIn = new ZipInputStream(new FileInputStream(file));
				File outFile = new File(args[1], file.getName());
				ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(outFile));
				zipOut.setLevel(9);
				ZipEntry entry;
				while((entry = zipIn.getNextEntry()) != null) {
					if (!entry.isDirectory() && entry.getName().toLowerCase(Locale.US).endsWith(".class")) {
						System.out.println("\tProcessing CLASS: "+entry.getName());
						ClassReader cr = new ClassReader(zipIn);
						ClassWriter cw = new ClassWriter(0);
						final boolean[] skip = new boolean[1];
						ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) { 
							@Override
							public void visit(int version, int access,
									String name, String signature,
									String superName, String[] interfaces) {
								if (!INCLUDE_PACKAGE_PRIVATE_CLASSES && (access & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED)) == 0){
									skip[0] = true;
								}
								super.visit(version, access, name, signature, superName, interfaces);
							}
							
							@Override
							public void visitSource(String source, String debug) {
								// ignore
							}
							
							@Override
							public FieldVisitor visitField(int access,
									String name, String desc, String signature,
									Object value) {
								if ((access & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED)) == 0)
									return null;
								return super.visitField(access, name, desc, signature, value);
							}
							
							@Override
							public MethodVisitor visitMethod(int access,
									String name, String desc, String signature,
									String[] exceptions) {
								if ((access & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED)) == 0)
									return null;
								return new MethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, desc, signature, exceptions)) {
									@Override
									public void visitCode() {
										// ignore
										super.visitCode();
										super.visitInsn(Opcodes.NOP);
									}
									
									@Override
									public void visitFieldInsn(int opcode,
											String owner, String name,
											String desc) {
										// ignore
									}
									
									@Override
									public void visitIincInsn(int var,
											int increment) {
										// ignore
									}
									
									@Override
									public void visitFrame(int type,
											int nLocal, Object[] local,
											int nStack, Object[] stack) {
										// ignore
									}
									
									@Override
									public void visitInsn(int opcode) {
										// ignore
									}
									
									@Override
									public void visitJumpInsn(int opcode,
											Label label) {
										// ignore
									}
									
									@Override
									public void visitLabel(Label label) {
										// ignore
									}
									
									@Override
									public void visitLdcInsn(Object cst) {
										// ignore
									}
									
									@Override
									public void visitLookupSwitchInsn(
											Label dflt, int[] keys,
											Label[] labels) {
										// ignore
									}
									
									@Override
									public void visitIntInsn(int opcode,
											int operand) {
										// ignore
									}
									
									@Override
									public AnnotationVisitor visitInsnAnnotation(
											int typeRef, TypePath typePath,
											String desc, boolean visible) {
										// ignore
										return null;
									}
									
									@Override
									public void visitInvokeDynamicInsn(
											String name, String desc,
											Handle bsm, Object... bsmArgs) {
										// ignore
									}
									
									@Override
									public void visitMethodInsn(int opcode,
											String owner, String name,
											String desc) {
										// ignore
									}
									
									@Override
									public void visitMultiANewArrayInsn(
											String desc, int dims) {
										// ignore
									}
									
									@Override
									public void visitTableSwitchInsn(int min,
											int max, Label dflt,
											Label... labels) {
										// ignore
									}
									
									@Override
									public AnnotationVisitor visitTryCatchAnnotation(
											int typeRef, TypePath typePath,
											String desc, boolean visible) {
										// ignore
										return null;
									};
									
									@Override
									public void visitTryCatchBlock(Label start,
											Label end, Label handler,
											String type) {
										// ignore
									}
									
									@Override
									public void visitLineNumber(int line,
											Label start) {
										// ignore
									}
									
									@Override
									public AnnotationVisitor visitLocalVariableAnnotation(
											int typeRef, TypePath typePath,
											Label[] start, Label[] end,
											int[] index, String desc,
											boolean visible) {
										// ignore
										return null;
									}
									
									@Override
									public void visitParameter(String name,
											int access) {
										// ignore
									}
									
									@Override
									public AnnotationVisitor visitParameterAnnotation(
											int parameter, String desc,
											boolean visible) {
										// ignore
										return null;
									}
									
									@Override
									public void visitTypeInsn(int opcode,
											String type) {
										// ignore
									}
									
									@Override
									public void visitVarInsn(int opcode, int var) {
										// ignore
									}
									
								};
							}
							
							@Override
							public void visitAttribute(Attribute attr) {
								System.out.println("\t\tProcessing attr "+attr.type);
								if (attr.isCodeAttribute()) return;
								
								super.visitAttribute(attr);
							}
						};
						cr.accept(cv, 0);
						if (!skip[0]) {
							byte[] b2 = cw.toByteArray(); // b2 represents the same class as b1
							entry.setSize(b2.length);
							entry.setCompressedSize(-1);
							entry.setMethod(ZipEntry.DEFLATED);
							zipOut.putNextEntry(entry);
							new DataOutputStream(zipOut).write(b2);
						}
					} else {
						
					}
				}
				zipIn.close();
				zipOut.close();
			}
		}
	}
}
