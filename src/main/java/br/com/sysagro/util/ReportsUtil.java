package br.com.sysagro.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Component
public class ReportsUtil {
	

	public void imprimir(HttpServletResponse response, List<?> lista, String fileName) throws JRException, SQLException, IOException {
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("createdBy", "SysAgro - Sistema de Gestão Agropecuário");
		
		JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(lista);
		File file = ResourceUtils.getFile("classpath:reports/"+fileName+".jrxml");
		
		JasperReport compileReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JasperPrint print = JasperFillManager.fillReport(compileReport, parametros, beanDS);
		
		JRPdfExporter pdfExporter = new JRPdfExporter();
		pdfExporter.setExporterInput(new SimpleExporterInput(print));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		pdfExporter.exportReport();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Length", String.valueOf(outputStream.size()));
		response.addHeader("Content-Disposition", "inline;filename=relatorio.pdf;");

		OutputStream stream = response.getOutputStream();
		stream.write(outputStream.toByteArray());
		stream.close();
		outputStream.close();		
	}

}
