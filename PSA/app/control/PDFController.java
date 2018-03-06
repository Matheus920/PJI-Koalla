
package app.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public abstract class PDFController{
    public static void mergeDocuments(List<String> caminhos, String[] conteudoPadrao, String caminhoSaida) throws IOException{
        List<File> toMergeFiles = new ArrayList<>();
        List<PDDocument> toMergeDocuments = new ArrayList<>();
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        
        for(String a : caminhos){
            toMergeFiles.add(new File(a));
        }
        
        for(File a : toMergeFiles){
            toMergeDocuments.add(PDDocument.load(a));

        }
        
        pdfMerger.setDestinationFileName(caminhoSaida);
        
        
        
        for(File a : toMergeFiles){
            pdfMerger.addSource(a);
        }
        
        pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        
        for(PDDocument a : toMergeDocuments){
            a.close();
        }
    
        PDDocument resultedDocument = PDDocument.load(new File(caminhoSaida));
      
        
        PDPageTree allPages = resultedDocument.getDocumentCatalog().getPages();
        PDPage page = new PDPage();
        
        allPages.insertBefore(page, allPages.get(0));
        
        PDPageContentStream content = new PDPageContentStream(resultedDocument, page, PDPageContentStream.AppendMode.PREPEND, true, false);
        
        content.beginText();
        
        content.setFont(PDType1Font.TIMES_ROMAN, 12);
        content.setLeading(14.5f);
        
        content.newLineAtOffset(25, 500);
        
        content.showText(conteudoPadrao[0]);
        content.newLine();
        content.showText(conteudoPadrao[1]);
        
        content.endText();
        content.close();
        resultedDocument.save(new File(caminhoSaida));
        resultedDocument.close();
    }
    
    public static void downloadDocument(String caminhoDocumento, String caminhoSaida, String nomeDoArquivo) throws IOException{
        File file = new File(caminhoDocumento);
        
        PDDocument document = PDDocument.load(file);
        
        document.save(new File(caminhoSaida + "\\" +nomeDoArquivo.replaceAll(" ", "") + ".pdf"));
        
        document.close();
    }
}