
import java.util.*;

public class Calculo {
    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String ip;
        int mascara;
        String[] ipBinario;
        String[] mascaraIP;
        String[] mascaraIPBin;
        String[] enderecoRede;
        String[] enderecoBroadcast;

        while (true) {
            // ------entrada
            System.out.println("Digite o IP:");
            ip = scanner.nextLine();
            System.out.println("digite a máscara de sub-rede:");
            mascara = scanner.nextInt();

            // ------endereco ip e mascara----

            System.out.println("Endereço IP e máscara de sub-rede:");
            System.out.println(ip + '/' + mascara);
            // ------endereco ip em binário----

            ipBinario = calcularEnderecoIpEmBin(ip);
            System.out.println("Endereço IP em binário:");
            System.out.println(String.join(".", ipBinario));

            // ------Sub-rede----

            System.out.println("Máscara de sub-rede em binário:");
            // calcula mascara de rede
            mascaraIPBin = calcularMascaraIp(mascara);
            System.out.println(String.join(".", mascaraIPBin));
            // calcula a mascara de sub-rede em dec
            mascaraIP = calcularMascaraIp(mascara);
            // imprime a máscara de sub-rede em decimal
            System.out.println("Máscara de sub-rede em decimal:");
            System.out.println(String.join(".", mascaraIP));

            // ------Endereço de rede----

            System.out.println("Endereço de rede:");
            // calcular Endereco de Rede
            enderecoRede = calcularEnderecoRede(ipBinario, mascaraIPBin);
            // imprimir Endereco de rede
            System.out.println(String.join(".", enderecoRede));

            // ------Endereço de broadcast----

            System.out.println("Endereço de broadcast:");
            enderecoBroadcast = calcularEnderecoBroadcast(mascaraIPBin, ipBinario);
            // calcular Endereco de Broadcast
            System.out.println(String.join(".", enderecoBroadcast));
            scanner.nextLine();
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
        }
    }

    public static String[] calcularEnderecoIpEmBin(String ip) {
        String[] ipBinario = new String[4];
        String[] octetos = ip.split("\\.");

        for (int i = 0; i < octetos.length; i++) {
            int octetoDecimal = Integer.parseInt(octetos[i]);
            String octetoBinario = Integer.toBinaryString(octetoDecimal);
            // Preenche com zeros à esquerda para garantir 8 bits
            while (octetoBinario.length() < 8) {
                octetoBinario = "0" + octetoBinario;
            }
            ipBinario[i] = octetoBinario;
        }

        return ipBinario;

    }

    private static String[] calcularMascaraIp(int mascara) {
        String[] mascaraIPBin = new String[4];
        int[] mascaraIP = new int[4];
        int bits = 0;
        for (int i = 0; i < 4; i++) {
            if (bits + 8 <= mascara) {
                mascaraIP[i] = 255;
                bits += 8;
            } else {
                int diff = mascara - bits;
                int octetoDecimal = 0;
                for (int j = 0; j < diff; j++) {
                    octetoDecimal |= (1 << (7 - j));
                }
                mascaraIP[i] = octetoDecimal;
                bits += diff;
            }
        }
        for (int i = 0; i < mascaraIP.length; i++) {
            String octetoBinario = Integer.toBinaryString(mascaraIP[i]);
            // Preenche com zeros à esquerda para garantir 8 bits
            while (octetoBinario.length() < 8) {
                octetoBinario = "0" + octetoBinario;
            }
            mascaraIPBin[i] = octetoBinario;
        }
        return mascaraIPBin;
    }

    private static String[] calcularEnderecoRede(String[] ipBinario, String[] mascaraIPBin) {
      
      
        if (ipBinario == null || mascaraIPBin == null) {
            throw new IllegalArgumentException("Os vetores não podem ser nulos.");
        }
        
        if (ipBinario.length != mascaraIPBin.length) {
            throw new IllegalArgumentException("Os vetores devem ter o mesmo tamanho.");
        }
        
        String[] vetorC = new String[ipBinario.length];
        
        // Percorre cada par de octetos nos vetores
        for (int i = 0; i < ipBinario.length; i++) {
            String octetoA = ipBinario[i];
            String octetoB = mascaraIPBin[i];
            
            if (octetoA == null || octetoB == null || octetoA.length() != octetoB.length()) {
                throw new IllegalArgumentException("Octetos inválidos ou com tamanhos diferentes no índice " + i);
            }
            
            StringBuilder resultadoOcteto = new StringBuilder();
            
            // Percorre cada bit dentro do octeto
            for (int j = 0; j < octetoA.length(); j++) {
                char bitA = octetoA.charAt(j);
                char bitB = octetoB.charAt(j);
                
                // Operação AND lógica entre os caracteres '1' e '0'
                if (bitA == '1' && bitB == '1') {
                    resultadoOcteto.append('1');
                } else {
                    resultadoOcteto.append('0');
                }
            }
            
            // Armazena o octeto resultante no terceiro vetor
            vetorC[i] = resultadoOcteto.toString();
        }
        
        return vetorC;
    }

    private static String[] calcularEnderecoBroadcast(String[] mascaraIPBin, String[] ipBinario) {
     if (mascaraIPBin == null || ipBinario == null) {
            throw new IllegalArgumentException("Os vetores não podem ser nulos.");
        }
        
        if (mascaraIPBin.length != ipBinario.length) {
            throw new IllegalArgumentException("Os vetores devem ter o mesmo tamanho.");
        }
        
        String[] vetorC = new String[mascaraIPBin.length];
        
        // Percorre cada par de octetos nos vetores
        for (int i = 0; i < mascaraIPBin.length; i++) {
            String octetoA = mascaraIPBin[i];
            String octetoB = ipBinario[i];
            
            if (octetoA == null || octetoB == null || octetoA.length() != octetoB.length()) {
                throw new IllegalArgumentException("Octetos inválidos ou com tamanhos diferentes no índice " + i);
            }
            
            StringBuilder resultadoOcteto = new StringBuilder();
            
            // Percorre cada bit dentro do octeto
            for (int j = 0; j < octetoA.length(); j++) {
                char bitA = octetoA.charAt(j);
                char bitB = octetoB.charAt(j);
                
                // Operação OR lógica: se pelo menos um for '1', o resultado é '1'
                if (bitA == '1' || bitB == '1') {
                    resultadoOcteto.append('1');
                } else {
                    resultadoOcteto.append('0');
                }
            }
            
            // Armazena o octeto resultante no terceiro vetor
            vetorC[i] = resultadoOcteto.toString();
        }
        
        return vetorC;
    }
    }

   