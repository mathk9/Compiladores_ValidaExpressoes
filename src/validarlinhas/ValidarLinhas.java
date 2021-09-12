package validarlinhas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;

public class ValidarLinhas {
    
    private File arquivo = new File("Expressoes.txt");
    private File arquivo_result = new File("Resultado_Expressao.txt");
    
    public static void main(String[] args) throws IOException {
        ValidarLinhas v = new ValidarLinhas();

        ArrayList<String> text = v.Readtxt();
        v.WriteOutput(text, v);
    }   
    
    public void WriteOutput(ArrayList<String> text, ValidarLinhas v) throws IOException{
        ArrayList<String> save = new ArrayList();
        
        FileWriter fw = new FileWriter(arquivo_result);
        BufferedWriter bw = new BufferedWriter( fw );
        
        for(int i = 0; i<text.size(); i++){
            if(v.VerifyLine(text.get(i)) == false){
                text.set(i, text.get(i) + " - Inválido");                
            }
            else{
                text.set(i, text.get(i) + " - OK");
            }
            System.out.println(text.get(i));
            save.add(text.get(i));
        }  
        for(int i = 0; i<save.size(); i++){
            bw.write(save.get(i));
            bw.newLine();
        }
        bw.close();
        fw.close();
    }
        
    public boolean VerifyLine(String line){
        try{
            Stack<String> leftSymbols = new Stack<>();
            int cont = 0;        
            for(String c : line.split("")){
                if ("(".equals(c) || "[".equals(c) || "{".equals(c) || "<".equals(c)){           
                    cont += 1;
                    leftSymbols.push(c);
                }

                else if ((")".equals(c) && "(".equals(leftSymbols.peek())) ||
                        ("]".equals(c) && "[".equals(leftSymbols.peek())) ||
                        ("}".equals(c) && "{".equals(leftSymbols.peek())) ||
                        (">".equals(c) && "<".equals(leftSymbols.peek()))){
                    cont -= 1;
                    leftSymbols.pop();
                }                

                if (cont < 0){
                    return false;
                }               
            }

            if (0 == cont || leftSymbols.isEmpty()){
                return true;
            }
            else{  
                return false;
            } 
        }
        catch(Exception e){
            return false;
        } 
    }
    
    public ArrayList<String> Readtxt() throws FileNotFoundException, IOException{
        try{
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader( fr );
            
            ArrayList<String> salvar = new ArrayList();
            Scanner in = new Scanner(new FileReader(arquivo));
            
            while (in.hasNextLine()) {
                salvar.add(in.nextLine()); 
            }

            br.close();
            fr.close(); 
            return salvar; 
        }
        catch(Exception e){
            System.out.println("ERRO" + e);
            return null;
        }               
    }    
}