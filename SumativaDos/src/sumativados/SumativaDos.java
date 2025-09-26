/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sumativados;

import java.util.Scanner;

/**
 *
 * @author pablo
 * Solo se utilizaron matrices y operadores de caracteres como %f.0 para imprimir precio, ademas se dio uso de la funcion repeat en la impresion de Boleta
 * junto con la importacion de la fecha para una mayor legibilidad del usuario.
 */
public class SumativaDos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Inicializacion de variables
        Scanner sc = new Scanner(System.in);
        String nombreTeatro = "Teatro Moro";
        int filas = 10;
        int asientosPorFila = 12;
        int capacidadTotal = filas * asientosPorFila;
        double precio = 25000;
        
        int totalAsientosDisponibles = capacidadTotal;
        int totalAsientosReservados = 0;
        int totalAsientosVendidos = 0;
        
        //Leyenda: 0 = disponible 1 = rservado 2 = vendido
        int[][] estadoAsientos = new int[filas][asientosPorFila];
        
        //Variables de control
        int opcionMenu = 0;
        int numeroAsiento = 0;
        int filaAsiento, columnaAsiento;
        int cantidadEntradas = 0;
        boolean continuar = true;
        boolean entradaValida;
        
        System.out.println("Bienvenido a sistema de venta de entradas de " + nombreTeatro + "!");
        System.out.println("Capacidad total: " + capacidadTotal + " asientos");
        System.out.println("Precio por entrada: $" + (int)precio);
        System.out.println();
        
        //MENU PRINCIPAL 
        
        while (continuar) {
            
            System.out.println("MENU PRINCIPAL: ");
            System.out.println("1. Reservar un asiento");
            System.out.println("2. Modificar una reserva");
            System.out.println("3. Comprar entradas");
            System.out.println("4. Imprimir boleta");
            System.out.println("5. Ver disponibilidad");
            System.out.println("0. Salir");
            
            //Validar opcion usuario
            entradaValida = false;
            while (!entradaValida) {
                System.out.print("Seleccione una opción (0-5): ");
                if (sc.hasNextInt()) {
                    opcionMenu = sc.nextInt();
                    if (opcionMenu >= 0 && opcionMenu <= 5) {
                        entradaValida = true;
                    } else {
                        System.out.println("Ingrese un número entre 0 y 5");
                    }
                } else {
                    System.out.println("Ingrese un caracter valido");
                    sc.next(); // Limpiar entrada inválida
                }
            }
            sc.nextLine(); //Aun no se que hace esto pero lo recomiendan para limpiar el buffer
            
            
            switch (opcionMenu) {
                case 1:
                    System.out.println("RESERVA DE ASIENTOS: ");
                    
                    if (totalAsientosDisponibles == 0) {
                        System.out.println("Lo sentimos, no hay asientos disponibles para reservar");
                        break;
                    }
                    
                    // Mostrar asientos
                    System.out.println("\nMapa de asientos (D=Disponible, R=Reservado, V=Vendido):");
                    for (int i = 0; i < filas; i++) {
                        System.out.printf("Fila %2d: ", (i + 1));
                        for (int j = 0; j < asientosPorFila; j++) {
                            int numeroActual = (i * asientosPorFila) + j + 1;
                            char estado = (estadoAsientos[i][j] == 0) ? 'D' : 
                                         (estadoAsientos[i][j] == 1) ? 'R' : 'V';
                            System.out.printf("%3d%c ", numeroActual, estado);
                        }
                        System.out.println();
                    }
                    
                    // Validación de número de asiento
                    entradaValida = false;
                    while (!entradaValida) {
                        System.out.print("\nIngrese número de asiento (1-" + capacidadTotal + "): ");
                        if (sc.hasNextInt()) {
                            numeroAsiento = sc.nextInt();
                            if (numeroAsiento >= 1 && numeroAsiento <= capacidadTotal) {
                                entradaValida = true;
                            } else {
                                System.out.println("Error: Número de asiento debe estar entre 1 y " + capacidadTotal);
                            }
                        } else {
                            System.out.println("Ingrese un caracter valido");
                            sc.next();
                        }
                    }
                    sc.nextLine();
                    
                    // Convertir número de asiento a fila y columna para esto inicializamos la matriz
                    filaAsiento = (numeroAsiento - 1) / asientosPorFila;
                    columnaAsiento = (numeroAsiento - 1) % asientosPorFila;
                    
                    // Verificar disponibilidad
                    if (estadoAsientos[filaAsiento][columnaAsiento] == 0) {
                        estadoAsientos[filaAsiento][columnaAsiento] = 1;
                        totalAsientosDisponibles--;
                        totalAsientosReservados++;
                        
                        System.out.println("Reserva exitosa!");
                        System.out.println("Asiento " + numeroAsiento + " (Fila " + (filaAsiento + 1) + 
                                         ", Posición " + (columnaAsiento + 1) + ")");
                        System.out.println("RECUERDE CONFIRMAR SU COMPRA!");
                    } else {
                        String estadoTexto = (estadoAsientos[filaAsiento][columnaAsiento] == 1) ? 
                                            "reservado" : "vendido";
                        System.out.println("El asiento " + numeroAsiento + " ya está " + estadoTexto);
                    }
                    break;
                    
                case 2:
                    System.out.println("\nMODIFICAR RESERVAS");
                    
                    if (totalAsientosReservados == 0) {
                        System.out.println("No hay reservas activas en el sistema");
                        break;
                    }
                    
                    // Mostrar asientos reservados
                    System.out.println("Asientos reservados disponibles:");
                    boolean hayReservas = false;
                    for (int i = 0; i < filas; i++) {
                        for (int j = 0; j < asientosPorFila; j++) {
                            if (estadoAsientos[i][j] == 1) {
                                int numeroReservado = (i * asientosPorFila) + j + 1;
                                System.out.print("Asiento " + numeroReservado + "  ");
                                hayReservas = true;
                            }
                        }
                    }
                    
                    if (!hayReservas) {
                        System.out.println("No hay reservas para modificar");
                        break;
                    }
                    
                    System.out.println();
                    
                    // Validar asiento a modificar
                    entradaValida = false;
                    int asientoAModificar = 0;
                    while (!entradaValida) {
                        System.out.print("Ingrese el número del asiento reservado: ");
                        if (sc.hasNextInt()) {
                            asientoAModificar = sc.nextInt();
                            if (asientoAModificar >= 1 && asientoAModificar <= capacidadTotal) {
                                int filaModificar = (asientoAModificar - 1) / asientosPorFila;
                                int columnaModificar = (asientoAModificar - 1) % asientosPorFila;
                                
                                if (estadoAsientos[filaModificar][columnaModificar] == 1) {
                                    entradaValida = true;
                                    
                                    // Liberar asiento actual
                                    estadoAsientos[filaModificar][columnaModificar] = 0;
                                    totalAsientosReservados--;
                                    totalAsientosDisponibles++;
                                    
                                  
                                    System.out.println("1. Reservar otro asiento");
                                    System.out.println("2. Confirmar compra de otro asiento");
                                    
                                    // Validar opción de modificación
                                    boolean opcionValida = false;
                                    while (!opcionValida) {
                                        System.out.print("Seleccione opción (1-2): ");
                                        if (sc.hasNextInt()) {
                                            int opcionModificar = sc.nextInt();
                                            if (opcionModificar == 1 || opcionModificar == 2) {
                                                opcionValida = true;
                                                
                                                // Seleccionar nuevo asiento
                                                boolean nuevoAsientoValido = false;
                                                while (!nuevoAsientoValido) {
                                                    System.out.print("Ingrese nuevo número de asiento (1-" + capacidadTotal + "): ");
                                                    if (sc.hasNextInt()) {
                                                        int nuevoAsiento = sc.nextInt();
                                                        if (nuevoAsiento >= 1 && nuevoAsiento <= capacidadTotal) {
                                                            int nuevaFila = (nuevoAsiento - 1) / asientosPorFila;
                                                            int nuevaColumna = (nuevoAsiento - 1) % asientosPorFila;
                                                            
                                                            if (estadoAsientos[nuevaFila][nuevaColumna] == 0) {
                                                                if (opcionModificar == 1) {
                                                                    // Nueva reserva
                                                                    estadoAsientos[nuevaFila][nuevaColumna] = 1;
                                                                    totalAsientosReservados++;
                                                                    totalAsientosDisponibles--;
                                                                    System.out.println("¡Nueva reserva exitosa en asiento " + nuevoAsiento + "!");
                                                                } else {
                                                                    // Compra directa
                                                                    estadoAsientos[nuevaFila][nuevaColumna] = 2;
                                                                    totalAsientosVendidos++;
                                                                    totalAsientosDisponibles--;
                                                                    System.out.println("¡Compra exitosa del asiento " + nuevoAsiento + "!");
                                                                    System.out.println("Total pagado: $" + (int)precio);
                                                                }
                                                                nuevoAsientoValido = true;
                                                            } else {
                                                                System.out.println("Asiento " + nuevoAsiento + " no disponible");
                                                            }
                                                        } else {
                                                            System.out.println("Ingrese un número entre 1 y " + capacidadTotal);
                                                        }
                                                    } else {
                                                        System.out.println("Ingrese un caracter valido");
                                                        sc.next();
                                                    }
                                                }
                                            } else {
                                                System.out.println("Porfavor Seleccione 1 o 2");
                                            }
                                        } else {
                                            System.out.println("Ingrese un caracter valido");
                                            sc.next();
                                        }
                                    }
                                } else {
                                    System.out.println("El asiento " + asientoAModificar + " no está reservado");
                                }
                            } else {
                                System.out.println("Ingre un número entre 1 y " + capacidadTotal);
                            }
                        } else {
                            System.out.println("Ingrese un caracter valido");
                            sc.next();
                        }
                    }
                    sc.nextLine();
                    break;
                    
                case 3:
                    System.out.println("COMPRAR ENTRADAS: ");
                    
                    if (totalAsientosDisponibles == 0 && totalAsientosReservados == 0) {
                        System.out.println("Lo sentimos, no hay asientos disponibles");
                        break;
                    }
                    
                    System.out.println("1. Comprar desde reserva existente");
                    System.out.println("2. Compra directa");
                    
                    // Validar tipo de compra
                    entradaValida = false;
                    int tipoCompra = 0;
                    while (!entradaValida) {
                        System.out.print("Seleccione tipo de compra (1-2): ");
                        if (sc.hasNextInt()) {
                            tipoCompra = sc.nextInt();
                            if (tipoCompra == 1 || tipoCompra == 2) {
                                entradaValida = true;
                            } else {
                                System.out.println("Porfavor Seleccione 1 o 2");
                            }
                        } else {
                            System.out.println("Ingrese un caracter valido");
                            sc.next();
                        }
                    }
                    sc.nextLine();
                    
                    if (tipoCompra == 1) {
                        // Compra desde reserva
                        if (totalAsientosReservados == 0) {
                            System.out.println("No hay reservas activas para confirmar");
                            break;
                        }
                        
                        System.out.println("Asientos reservados:");
                        for (int i = 0; i < filas; i++) {
                            for (int j = 0; j < asientosPorFila; j++) {
                                if (estadoAsientos[i][j] == 1) {
                                    int numeroReservado = (i * asientosPorFila) + j + 1;
                                    System.out.print("Asiento " + numeroReservado + "  ");
                                }
                            }
                        }
                        System.out.println();
                        
                        entradaValida = false;
                        while (!entradaValida) {
                            System.out.print("Ingrese número del asiento reservado a comprar: ");
                            if (sc.hasNextInt()) {
                                numeroAsiento = sc.nextInt();
                                if (numeroAsiento >= 1 && numeroAsiento <= capacidadTotal) {
                                    filaAsiento = (numeroAsiento - 1) / asientosPorFila;
                                    columnaAsiento = (numeroAsiento - 1) % asientosPorFila;
                                    
                                    if (estadoAsientos[filaAsiento][columnaAsiento] == 1) {
                                        estadoAsientos[filaAsiento][columnaAsiento] = 2;
                                        totalAsientosReservados--;
                                        totalAsientosVendidos++;
                                        
                                        System.out.println("¡Compra exitosa!");
                                        System.out.println("Asiento " + numeroAsiento);
                                        System.out.println("Total pagado: $" + (int)precio);
                                        entradaValida = true;
                                    } else {
                                        System.out.println("El asiento " + numeroAsiento + " no está reservado");
                                    }
                                } else {
                                    System.out.println("Ingrese un número entre 1 y " + capacidadTotal);
                                }
                            } else {
                                System.out.println("Ingrese caracter valido");
                                sc.next();
                            }
                        }
                        
                    } else {
                        // Compra directa
                        if (totalAsientosDisponibles == 0) {
                            System.out.println("No hay asientos disponibles para compra directa");
                            break;
                        }
                        
                        // Validar cantidad de entradas
                        entradaValida = false;
                        while (!entradaValida) {
                            System.out.print("Cantidad de entradas (1-" + totalAsientosDisponibles + "): ");
                            if (sc.hasNextInt()) {
                                cantidadEntradas = sc.nextInt();
                                if (cantidadEntradas >= 1 && cantidadEntradas <= totalAsientosDisponibles) {
                                    entradaValida = true;
                                } else {
                                    System.out.println("Ingrese un numero entre 1 y " + totalAsientosDisponibles);
                                }
                            } else {
                                System.out.println("Ingrese un caracter valido");
                                sc.next();
                            }
                        }
                        sc.nextLine();
                        
                        double totalPagar = cantidadEntradas * precio;
                        System.out.println("Total a pagar: $" + (int)totalPagar);
                        
                        // Estructura de control 2: Bucle para selección múltiple de asientos
                        for (int entrada = 0; entrada < cantidadEntradas; entrada++) {
                            System.out.println("\n--- Entrada " + (entrada + 1) + " de " + cantidadEntradas + " ---");
                            
                            entradaValida = false;
                            while (!entradaValida) {
                                System.out.print("Ingrese número de asiento (1-" + capacidadTotal + "): ");
                                if (sc.hasNextInt()) {
                                    numeroAsiento = sc.nextInt();
                                    if (numeroAsiento >= 1 && numeroAsiento <= capacidadTotal) {
                                        filaAsiento = (numeroAsiento - 1) / asientosPorFila;
                                        columnaAsiento = (numeroAsiento - 1) % asientosPorFila;
                                        
                                        if (estadoAsientos[filaAsiento][columnaAsiento] == 0) {
                                            estadoAsientos[filaAsiento][columnaAsiento] = 2;
                                            totalAsientosDisponibles--;
                                            totalAsientosVendidos++;
                                            
                                            System.out.println("Asiento " + numeroAsiento + " confirmado");
                                            entradaValida = true;
                                        } else {
                                            String estadoTexto = (estadoAsientos[filaAsiento][columnaAsiento] == 1) ? 
                                                                "reservado" : "vendido";
                                            System.out.println("Asiento " + numeroAsiento + " ya está " + estadoTexto);
                                        }
                                    } else {
                                        System.out.println("Ingrese un número entre 1 y " + capacidadTotal);
                                    }
                                } else {
                                    System.out.println("Ingrese un caracter valido");
                                    sc.next();
                                }
                            }
                        }
                        
                        System.out.println("\n¡Compra completada!");
                        System.out.println("Total pagado: $" + (int)totalPagar);
                    }
                    break;
                    
                case 4:
                    System.out.println("IMRPIMIR BOLETA");
                    
                    if (totalAsientosVendidos == 0) {
                        System.out.println("No hay entradas vendidas para imprimir");
                        break;
                    }
                    
                
                    //Boleta bonita 
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("           BOLETA DE ENTRADA");
                    System.out.println("             " + nombreTeatro);
                    System.out.println("=".repeat(50));
                    System.out.println("Fecha: " + new java.util.Date());
                    System.out.println("-".repeat(50));
                    
                    int numeroEntrada = 1;
                    double totalBoleta = 0;
                    
                    for (int i = 0; i < filas; i++) {
                        for (int j = 0; j < asientosPorFila; j++) {
                            if (estadoAsientos[i][j] == 2) {
                                int numeroAsientoVendido = (i * asientosPorFila) + j + 1;
                                System.out.printf("Entrada %d - Asiento: %3d | Precio: $%d%n", 
                                                numeroEntrada, numeroAsientoVendido, (int)precio);
                                totalBoleta += precio;
                                numeroEntrada++;
                            }
                        }
                    }
                    
                    System.out.println("-".repeat(50));
                    System.out.printf("TOTAL: $%.0f%n", totalBoleta);
                    System.out.println("Disfrute la funcion!");
                    System.out.println("=".repeat(50));
                    break;
                    
                case 5:
                    System.out.println("DISPONIBILIDAD DE ASIENTOS: ");
                    System.out.println("Leyenda: D=Disponible, R=Reservado, V=Vendido");
                    System.out.println();
                    
                    for (int i = 0; i < filas; i++) {
                        System.out.printf("Fila %2d: ", (i + 1));
                        for (int j = 0; j < asientosPorFila; j++) {
                            int numeroActual = (i * asientosPorFila) + j + 1;
                            char estado = (estadoAsientos[i][j] == 0) ? 'D' : 
                                         (estadoAsientos[i][j] == 1) ? 'R' : 'V';
                            System.out.printf("%3d%c ", numeroActual, estado);
                        }
                        System.out.println();
                    }
                    
                    System.out.println("RESUMEN");
                    System.out.println("Disponibles: " + totalAsientosDisponibles);
                    System.out.println("Reservados: " + totalAsientosReservados);
                    System.out.println("Vendidos: " + totalAsientosVendidos);
                    System.out.printf("Ocupación: %.1f%%%n", 
                                    ((double)(totalAsientosReservados + totalAsientosVendidos) / capacidadTotal) * 100);
                    break;
                    
                case 0:
                    System.out.println("¡Gracias por usar el sistema del " + nombreTeatro + "!");
                    continuar = false;
                    break;
            }
        }
    }
    
}
