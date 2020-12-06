package com.Tuong.EventSystem;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.Tuong.Authenication.AccountInfo;
import com.Tuong.DateUtils.Date;
import com.Tuong.Medicine.MedicinePrescription;
import com.Tuong.Patient.Patient;

public class PrescriptionEvent extends Event{
	private Date start, end;
	private ArrayList<MedicinePrescription> med_list;
	public PrescriptionEvent(Date start, Date end, ArrayList<MedicinePrescription> med_list, Patient patient){
		super(patient);
		this.start = start;
		this.end = end;
		this.med_list = med_list;
	}
	
	public Date getStart() {
		return start;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public ArrayList<MedicinePrescription> getPrescription(){
		return med_list;
	}
	
	public void print() {
		new PrintPrescription(this);
	}
	
	@Override
	public String toString() {
		return "Prescription "+start.day+"/"+start.month +" - "+ end.day+"/" + end.month;
	}
}

class PrintPrescription implements Printable {
	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        ImageIcon img = new ImageIcon("Data/logo_black.png");
        g.drawImage(img.getImage(), 0, 0, null);
        
        g.drawString("Bác sĩ: "+AccountInfo.current.getDisplayname(), 170, 30+20);
        g.drawString("Bệnh nhân: "+event.getPatient(), 170, 45+20);
        g.drawString("Ngày kê thuốc: "+event.getStart().toReadable(), 170, 60+20);
        g.drawString("Ngày tái khám: "+event.getEnd().toReadable(), 170, 75+20);
        drawStringMultiLine(g2d,"Chuẩn đoán: "+event.getPatient().getDiagnosis(), 200, 170, 90+20);
        
        int k = 1;
        int y = 150;
        
    	y+=Math.max(drawStringMultiLine(g2d, "Tên thuốc", 100, 40, y),
    	drawStringMultiLine(g2d, "Hoạt chất", 200, 150, y))+10;
    	g.drawRect(20, 130, 400-40, y-150);
    	g.drawRect(20, 130, 15, y-150);
    	g.drawRect(20, 130, 15+100, y-150);
    	
        for(MedicinePrescription pre : event.getPrescription()) {
        	g.drawString(k+"", 22, y);
        	y+=Math.max(drawStringMultiLine(g2d, pre.medicine.getName(), 100, 40, y),
        	drawStringMultiLine(g2d, pre.medicine.getHoatChat(), 200, 150, y))+10;
        	g.drawRect(20, 130, 400-40, y-150);
        	g.drawRect(20, 130, 15, y-150);
        	g.drawRect(20, 130, 15+100, y-150);
        	k++;
        }
        g.drawRect(20, 130, 400-40, y - 150);
        
        g.drawString("Chữ ký người kê toa", 40, y+30);
        
        return PAGE_EXISTS;
	}
	public static int drawStringMultiLine(Graphics2D g, String text, int lineWidth, int x, int y) {
	    FontMetrics m = g.getFontMetrics();
	    int old_y = y;
	    if(m.stringWidth(text) < lineWidth) {
	        g.drawString(text, x, y);
	    } else {
	        String[] words = text.split(" ");
	        String currentLine = words[0];
	        for(int i = 1; i < words.length; i++) {
	            if(m.stringWidth(currentLine+words[i]) < lineWidth) {
	                currentLine += " "+words[i];
	            } else {
	                g.drawString(currentLine, x, y);
	                y += m.getHeight();
	                currentLine = words[i];
	            }
	        }
	        if(currentLine.trim().length() > 0) {
	            g.drawString(currentLine, x, y);
	        }
	    }
	    return y-old_y+m.getHeight();
	}
	private PrescriptionEvent event;
	public PrintPrescription(PrescriptionEvent event) {
		this.event = event;
		PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                 job.print();
            } catch (PrinterException ex) {
            	ex.printStackTrace();
            }
        }
	}
}