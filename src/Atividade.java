import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Atividade {
  public static void main(String[] args) {

    String arquivo = "C:\\Users\\52350793885.SESISENAI\\Desktop\\pastabuffer\\ativos.csv";

    int windows = 0;
    int linux = 0;
    int macOS = 0;

    String dataAuditoria = LocalDate.now().toString();

    try (
            BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
            BufferedWriter escritor = new BufferedWriter(new FileWriter("inventario_atualizado.csv"));
            BufferedWriter escritorCriticos = new BufferedWriter(new FileWriter("hosts_criticos.csv"))
    ) {

      String linha = leitor.readLine();

      if (linha != null) {
        String cabecalho = linha + ",criticidade,status,data_auditoria,risco";

        escritor.write(cabecalho);
        escritor.newLine();

        escritorCriticos.write(cabecalho);
        escritorCriticos.newLine();
      }

      while ((linha = leitor.readLine()) != null) {

        String[] dados = linha.split(",");

        String host = dados[0];
        String ip = dados[1];
        String so = dados[2];

        String sistema = so.toLowerCase();

        String criticidade;

        if (so.contains("Kali")) {
          criticidade = "Alta";
        }
        else if (so.contains("Ubuntu") ||
                so.contains("Debian") ||
                so.contains("Rocky")) {
          criticidade = "Baixa";
        }
        else {
          criticidade = "Media";
        }

        // DESAFIO 1
        String status;

        if (sistema.contains("windows")) {
          status = "Online";
        }
        else if (sistema.contains("linux") ||
                sistema.contains("kali") ||
                sistema.contains("ubuntu") ||
                sistema.contains("debian") ||
                sistema.contains("rocky")) {
          status = "Em Monitoramento";
        }
        else {
          status = "Não definido";
        }

        // DESAFIO 2
        if (sistema.contains("windows")) {
          windows++;
        }
        else if (sistema.contains("linux") ||
                sistema.contains("kali") ||
                sistema.contains("ubuntu") ||
                sistema.contains("debian") ||
                sistema.contains("rocky")) {
          linux++;
        }
        else if (sistema.contains("mac")) {
          macOS++;
        }

        // DESAFIO 3
        if (criticidade.equals("Alta")) {
          escritorCriticos.write(
                  host + "," +
                          ip + "," +
                          so + "," +
                          criticidade + "," +
                          status + "," +
                          dataAuditoria + ",10"
          );

          escritorCriticos.newLine();
        }

        // DESAFIO 4
        String data = dataAuditoria;

        // DESAFIO 5
        int risco;

        if (criticidade.equals("Alta")) {
          risco = 10;
        }
        else if (criticidade.equals("Baixa")) {
          risco = 1;
        }
        else {
          risco = 5;
        }

        escritor.write(
                host + "," +
                        ip + "," +
                        so + "," +
                        criticidade + "," +
                        status + "," +
                        data + "," +
                        risco
        );

        escritor.newLine();
      }

      System.out.println("Quantidade de hosts Windows: " + windows);
      System.out.println("Quantidade de hosts Linux: " + linux);
      System.out.println("Quantidade de hosts macOS: " + macOS);

      System.out.println("Relatorio gerado com sucesso!");
      System.out.println("Arquivo atualizado: inventario_atualizado.csv");
      System.out.println("Arquivo de hosts criticos: hosts_criticos.csv");

    } catch (IOException e) {
      System.out.println("Erro: " + e.getMessage());
    }
  }
}