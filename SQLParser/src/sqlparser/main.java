/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlparser;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Astronaut
 */
public class main {

    /**
     * @param args the command line arguments
     */
    
    static void cekIsiTabel(String[] query, String[] isi) {
        ArrayList<String> outpoot = new ArrayList<String>();
        int p = 0;
        for (int i = 0; i<query.length; i++) {
            for (int j = 1; j<isi.length-1; j++) {
                if (query[i].equals(isi[j])) {
                    outpoot.add(isi[j]);
                    p++;
                }
            }
        }
        if (p == query.length) {
            for (int k = 0; k<p; k++) {
                if (k != p-1) {
                    System.out.print(outpoot.get(k)+", ");
                } else {
                    System.out.println(outpoot.get(k)); 
                }
                
            }
        } else {
            System.out.println("Error");
        }
    }
    
    static String[] parsing(String[] query){
        String[] sqlparse = query[1].split(",");
        return sqlparse;
    }
    
    
    static String tabelJoin(String[] tab1, String[] tab2, String attribut, String[] isiSQL) {
       
       try
       {
       boolean a = true;
       boolean b = true;
       int i=1;
       String[] pars = parsing(isiSQL);
       while ((a == true) || (b==true)){
           if (tab1[i].equals(attribut)) {
               a = false;
           }
           if (tab2[i].equals(attribut)) {
               b = false;
           }
           i++;
       }
       if (a==false && b==false){
           System.out.print("List Kolom 1: ");
           for (int j = 0; j<pars.length; j++) {
            for (int k = 1; k<tab1.length; k++) {
                if (pars[j].equals(tab1[k])) {
                    System.out.print(pars[j] + " ");
                }
//                System.out.println(pars[j].equals(tab1[k]));
            }

            }
                          System.out.println("");
           System.out.print("List Kolom 2: ");
           for (int j = 0; j<pars.length; j++) {
            for (int k = 1; k<tab2.length; k++) {
                if (pars[j].equals(tab2[k])) {
                    System.out.print(pars[j] + " ");
                }
//                System.out.println(pars[j].equals(tab2[k]));
            }
            }
        } return null;
       }
       catch(ArrayIndexOutOfBoundsException e)
       {
          System.out.println("Syntax Error");
          return null;
       } 
       
    }
    
    static String[] cekTabelJoin(String[] tbl1, String[] tbl2, String[] tbl3, String tblJoin) {
        String[] ret = null;
        if (tbl1[0].equals(tblJoin)) {
            ret =  tbl1;
        } else if (tbl2[0].equals(tblJoin)) {
            ret = tbl2;
        } else if (tbl3[0].equals(tblJoin)) {
            ret = tbl3;
        } 
        return ret;
    }
    
    static String cekAtribut(String att) {
        int start = 0; // '(' position in string
        int end = 0; // ')' position in string
        for(int i = 0; i < att.length(); i++) { 
                if(att.charAt(i) == '(') // Looking for '(' position in string
                    start = i;
                else if(att.charAt(i) == ')') // Looking for ')' position in  string
                    end = i;
        }
        String number = att.substring(start+1, end); // you take value between start and end
        return number;
    }
    
    
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        String fileName = "data.txt";   
        File file = new File(fileName);
        Scanner inputStream = new Scanner(file);            
        String data = inputStream.next();
        String[] Tabel = data.split("#");
        String[] isiTabel0 = Tabel[0].split(";");  //developer
        String[] isiTabel1 = Tabel[1].split(";");  //Client
        String[] isiTabel2 = Tabel[2].split(";");  // project
            
       // Handle SELECT atrribute from tabel
       Scanner sc = new Scanner(System.in);
       String SQL = sc.nextLine();  //INPUT SQL
       SQL.toLowerCase();
       
       String[] splitSQL = SQL.split(" ");  //Split SQL to Array
       
       String cekUsing = splitSQL[splitSQL.length-4];
       String tes = splitSQL[splitSQL.length-1];
       int len = splitSQL[splitSQL.length-1].length();
       // System.out.println(len);
       int lenSQL = splitSQL.length;
       char titikoma = tes.charAt(len-1);
       if (cekUsing.equals("join")) {
           if (titikoma != ';') {
               System.out.println("Error : Missing ;");
           } else if (splitSQL[0].equals("select") && splitSQL[splitSQL.length-2].equals("using") && splitSQL[splitSQL.length-4].equals("join") && splitSQL[splitSQL.length-6].equals("from")) {
                System.out.println("SQL join");
                String tabel1 = splitSQL[splitSQL.length-5];
                System.out.println("tabel 1 : " + tabel1);
                String tabel2 = splitSQL[splitSQL.length-3];
                System.out.println("tabel 2 : " + tabel2);
                String entitasJoin = splitSQL[splitSQL.length-1];
                //System.out.println("entitas join : "+entitasJoin);
                String[] cekcek = cekTabelJoin(isiTabel0, isiTabel1, isiTabel2, tabel1);
                System.out.println(cekcek[2]);
                String[] cekcek2 = cekTabelJoin(isiTabel0, isiTabel1, isiTabel2, tabel2);
                System.out.println(cekcek2[2]);
                String attr = cekAtribut(entitasJoin);
                System.out.println("att join : " + attr);
                tabelJoin(cekcek, cekcek2, attr, splitSQL);
             } else {
               System.out.println("Syntax error");
           }
         
       } else {

                  if (titikoma == ';') {
                        if (splitSQL[0].equals("select") && splitSQL[1].equals("*") && splitSQL[2].equals("from")) {
                            if (splitSQL.length == 4)  {
                                if (splitSQL[3].equals(isiTabel0[0] + ";")) {
                                    for (int i = 1; i<isiTabel0.length; i++) {
                                        if (i != isiTabel0.length-1) {
                                            System.out.print(isiTabel0[i] + ", ");
                                        } else {
                                            System.out.println(isiTabel0[i]);
                                        }
                            
                                    }
                                    } else if (splitSQL[3].equals(isiTabel1[0] + ";")) {
                                        for (int i = 1; i<isiTabel1.length; i++) {
                                            if (i != isiTabel0.length-1) {
                                                System.out.print(isiTabel1[i] + ", ");
                                                } else {
                                                    System.out.println(isiTabel1[i]);
                                                }
                                            }
                                        }  else if (splitSQL[3].equals(isiTabel2[0] + ";")) {
                                        for (int i = 1; i<isiTabel2.length; i++) {
                                            if (i != isiTabel0.length-1) {
                                                System.out.print(isiTabel2[i] + ", ");
                                            } else {
                                                System.out.println(isiTabel2[i]);
                                            }
                                        }
                                    }
                                } else {
                        System.out.println("Query Error");
                }
            } else if (splitSQL[0].equals("select") && splitSQL[lenSQL-2].equals("from")){
                        if (splitSQL[lenSQL-1].equals(isiTabel0[0] + ";")) {
                            cekIsiTabel(parsing(splitSQL), isiTabel0);
                        } else if (splitSQL[lenSQL-1].equals(isiTabel1[0] + ";")) {
                            cekIsiTabel(parsing(splitSQL), isiTabel1);
                        }  else if (splitSQL[lenSQL-1].equals(isiTabel2[0] + ";")) {
                            cekIsiTabel(parsing(splitSQL), isiTabel2);
                        }
            } 
           
           else {
               System.out.println("Syntax error");
           }
       } else {
                System.out.println("Missing ;");
            }
       }

       

      
       // SELECT JOIN USING
       //EX : SELECT NAMA_DEVELOPER,SKILL,NAMA_CLIENT FROM DEVELOPER JOIN PROJECT USING(ID_DEVEOPER);
       
       
        


        
    }
    
}
