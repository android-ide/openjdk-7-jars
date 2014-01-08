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
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

public class Main {
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
						ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) { 
							@Override
							public MethodVisitor visitMethod(int access,
									String name, String desc, String signature,
									String[] exceptions) {
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
						byte[] b2 = cw.toByteArray(); // b2 represents the same class as b1
						entry.setSize(b2.length);
						entry.setCompressedSize(-1);
						entry.setMethod(ZipEntry.DEFLATED);
						zipOut.putNextEntry(entry);
						new DataOutputStream(zipOut).write(b2);
					} else {
						
					}
				}
				zipIn.close();
				zipOut.close();
			}
		}
	}
}
