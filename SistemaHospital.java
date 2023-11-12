package semana11POOI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Medico {
    int numeroColegiatura;
    String nombre;
    String especialidad;

    public Medico(int numeroColegiatura, String nombre, String especialidad) {
        this.numeroColegiatura = numeroColegiatura;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }
}
class Paciente {
    int DNI;
    String nombre;
    String direccion;
    double peso;
    double temperatura;
    Medico medico;

    public Paciente(int DNI, String nombre, String direccion, double peso, double temperatura) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.direccion = direccion;
        this.peso = peso;
        this.temperatura = temperatura;
    }
}

public class SistemaHospital {
    private static ArrayList<Paciente> listaPacientes = new ArrayList<>();
    private static ArrayList<Medico> listaMedicos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Agregar algunos médicos de ejemplo
        listaMedicos.add(new Medico(1, "Dr. Pérez", "Cardiología"));
        listaMedicos.add(new Medico(2, "Dra. Rodríguez", "Pediatría"));
        listaMedicos.add(new Medico(3, "Dr. Gómez", "Cirugía"));
        int opcion;

        do {
            mostrarMenu();
            System.out.print("¿QUÉ OPERACIÓN DESEA EJECUTAR? ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    registrarPaciente();
                    break;
                case 2:
                    eliminarPaciente();
                    break;
                case 3:
                    modificarPaciente();
                    break;
                case 4:
                    mostrarPesoMasRepetido();
                    break;
                case 5:
                    mostrarCantidadPacientesPorPeso();
                    break;
                case 6:
                    mostrarPesoMayorYMenor();
                    break;
                case 7:
                    mostrarCantidadPacientesPorRangoDePesos();
                    break;
                case 8:
                    mostrarPacientesOrdenadosPorApellidos();
                    break;
                case 9:
                    mostrarDoctorQueAtendioPaciente();
                    break;
                case 10:
                    buscarDoctoresPorEspecialidad();
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
            }

            scanner.nextLine(); // Consumir el salto de línea pendiente

        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("******** SISTEMA DE GESTIÓN HOSPITALARIA ********");
        System.out.println("1. Registrar datos de paciente");
        System.out.println("2. Eliminar datos de paciente");
        System.out.println("3. Modificar datos de paciente");
        System.out.println("4. Mostrar peso que más se repite");
        System.out.println("5. Mostrar cantidad de pacientes por peso");
        System.out.println("6. Mostrar peso mayor y menor");
        System.out.println("7. Mostrar cantidad de pacientes por rango de pesos");
        System.out.println("8. Mostrar lista de pacientes ordenados por apellidos");
        System.out.println("9. Mostrar médico que atendió a un paciente");
        System.out.println("10. Buscar doctores por especialidad");
        System.out.println("0. Salir");
    }

    private static int obtenerEnteroDesdeEntrada(String mensaje) {
        int valor = 0;
        boolean entradaValida = false;

        do {
            System.out.print(mensaje);

            if (scanner.hasNextInt()) {
                valor = scanner.nextInt();
                entradaValida = true;
            } else {
                System.out.println("Carácter inválido. Por favor, ingrese un número.");
                scanner.next(); // Consumir la entrada incorrecta
            }

        } while (!entradaValida);

        return valor;
    }

    private static double obtenerDoubleDesdeEntrada(String mensaje) {
        double valor = 0;
        boolean entradaValida = false;

        do {
            System.out.print(mensaje);

            if (scanner.hasNextDouble()) {
                valor = scanner.nextDouble();
                entradaValida = true;
            } else {
                System.out.println("Carácter inválido. Por favor, ingrese un número.");
                scanner.next(); // Consumir la entrada incorrecta
            }

        } while (!entradaValida);

        return valor;
    }

    private static void registrarPaciente() {
        System.out.println("****** REGISTRAR DATOS DE PACIENTE ******");

        int dni = obtenerEnteroDesdeEntrada("Ingrese DNI: ");
        scanner.nextLine(); // Consumir el salto de línea pendiente

        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese dirección: ");
        String direccion = scanner.nextLine();

        double peso = obtenerDoubleDesdeEntrada("Ingrese peso: ");
        double temperatura = obtenerDoubleDesdeEntrada("Ingrese temperatura: ");

        // Mostrar lista de médicos disponibles
        System.out.println("Lista de médicos disponibles:");
        for (int i = 0; i < listaMedicos.size(); i++) {
            System.out.println((i + 1) + ". " + listaMedicos.get(i).nombre);
        }

        System.out.print("Seleccione el número de colegiatura del médico que atendió al paciente: ");
        int seleccionMedico = obtenerEnteroDesdeEntrada("");
        Medico medicoAtendio = listaMedicos.get(seleccionMedico - 1);

        Paciente nuevoPaciente = new Paciente(dni, nombre, direccion, peso, temperatura);
        nuevoPaciente.medico = medicoAtendio;

        listaPacientes.add(nuevoPaciente);
        System.out.println("¡Se registró el paciente con éxito!");
        scanner.nextLine(); // Consumir el salto de línea pendiente
    }

    private static void eliminarPaciente() {
        if (listaPacientes.isEmpty()) {
            System.out.println("No hay pacientes para eliminar.");
            return;
        }

        System.out.println("****** ELIMINAR DATOS DE PACIENTE ******");
        mostrarListaPacientes();

        System.out.print("Ingrese la posición del paciente que desea eliminar: ");
        int posicionEliminar = scanner.nextInt();

        if (posicionEliminar >= 1 && posicionEliminar <= listaPacientes.size()) {
            Paciente pacienteEliminado = listaPacientes.remove(posicionEliminar - 1);
            System.out.println("Se eliminó el paciente: " + pacienteEliminado.nombre);
        } else {
            System.out.println("Posición inválida.");
        }
    }

    private static void modificarPaciente() {
        if (listaPacientes.isEmpty()) {
            System.out.println("No hay pacientes para modificar.");
            return;
        }

        System.out.println("****** MODIFICAR DATOS DE PACIENTE ******");
        mostrarListaPacientes();

        System.out.print("Ingrese la posición del paciente que desea modificar: ");
        int posicionModificar = scanner.nextInt();

        if (posicionModificar >= 1 && posicionModificar <= listaPacientes.size()) {
            Paciente pacienteModificar = listaPacientes.get(posicionModificar - 1);
            System.out.print("Ingrese nuevo peso: ");
            double nuevoPeso = scanner.nextDouble();
            pacienteModificar.peso = nuevoPeso;
            System.out.println("Se modificó el peso del paciente: " + pacienteModificar.nombre);
        } else {
            System.out.println("Posición inválida.");
            
        }
    }
    

    private static void mostrarPesoMasRepetido() {
        if (listaPacientes.isEmpty()) {
            System.out.println("No hay pacientes para mostrar.");
            return;
        }

        Map<Double, Integer> frecuenciaPesos = new HashMap<>();

        for (Paciente paciente : listaPacientes) {
            double peso = paciente.peso;
            frecuenciaPesos.put(peso, frecuenciaPesos.getOrDefault(peso, 0) + 1);
        }

        double pesoMasRepetido = 0;
        int maxFrecuencia = 0;

        for (Map.Entry<Double, Integer> entry : frecuenciaPesos.entrySet()) {
            if (entry.getValue() > maxFrecuencia) {
                maxFrecuencia = entry.getValue();
                pesoMasRepetido = entry.getKey();
            }
        }

        System.out.println("El peso que más se repite es: " + pesoMasRepetido);
    }
    private static void mostrarCantidadPacientesPorPeso() {
        if (listaPacientes.isEmpty()) {
            System.out.println("No hay pacientes para mostrar.");
            return;
        }

        Map<Double, Integer> cantidadPacientesPorPeso = new HashMap<>();

        for (Paciente paciente : listaPacientes) {
            double peso = paciente.peso;
            cantidadPacientesPorPeso.put(peso, cantidadPacientesPorPeso.getOrDefault(peso, 0) + 1);
        }

        System.out.println("Cantidad de pacientes por peso:");
        for (Map.Entry<Double, Integer> entry : cantidadPacientesPorPeso.entrySet()) {
            System.out.println("Peso: " + entry.getKey() + " kg - Cantidad: " + entry.getValue());
        }
    }
    private static void mostrarPesoMayorYMenor() {
        if (listaPacientes.isEmpty()) {
            System.out.println("No hay pacientes para mostrar.");
            return;
        }

        double pesoMayor = Double.MIN_VALUE;
        double pesoMenor = Double.MAX_VALUE;

        for (Paciente paciente : listaPacientes) {
            double peso = paciente.peso;
            if (peso > pesoMayor) {
                pesoMayor = peso;
            }
            if (peso < pesoMenor) {
                pesoMenor = peso;
            }
        }

        System.out.println("Peso mayor: " + pesoMayor + " kg");
        System.out.println("Peso menor: " + pesoMenor + " kg");
    }


    private static void mostrarCantidadPacientesPorRangoDePesos() {
        if (listaPacientes.isEmpty()) {
            System.out.println("No hay pacientes para mostrar.");
            return;
        }

        int numRangos = 4;
        double pesoMinimo = Double.MAX_VALUE;
        double pesoMaximo = Double.MIN_VALUE;

        // Encontrar el peso mínimo y máximo en la lista de pacientes
        for (Paciente paciente : listaPacientes) {
            double peso = paciente.peso;
            pesoMinimo = Math.min(pesoMinimo, peso);
            pesoMaximo = Math.max(pesoMaximo, peso);
        }

        // Calcular el tamaño de cada rango
        double rango = (pesoMaximo - pesoMinimo) / numRangos;

        // Crear un mapa para almacenar la cantidad de pacientes en cada rango
        Map<Integer, Integer> pacientesPorRango = new HashMap<>();

        // Iterar sobre la lista de pacientes y contar la cantidad en cada rango
        for (Paciente paciente : listaPacientes) {
            double peso = paciente.peso;
            int rangoIndex = (int) ((peso - pesoMinimo) / rango) + 1;
            pacientesPorRango.put(rangoIndex, pacientesPorRango.getOrDefault(rangoIndex, 0) + 1);
        }

        // Mostrar la cantidad de pacientes en cada rango
        System.out.println("Cantidad de pacientes por rango de pesos:");
        for (Map.Entry<Integer, Integer> entry : pacientesPorRango.entrySet()) {
            double rangoInicio = pesoMinimo + (entry.getKey() - 1) * rango;
            double rangoFin = pesoMinimo + entry.getKey() * rango;
            System.out.println("Rango " + entry.getKey() + ": " + entry.getValue() + " pacientes (" + rangoInicio + " kg - " + rangoFin + " kg)");
        }
    }


    private static void mostrarPacientesOrdenadosPorApellidos() {
        if (listaPacientes.isEmpty()) {
            System.out.println("No hay pacientes para mostrar.");
            return;
        }

        // Utilizar un comparador personalizado para ordenar por apellidos
        Comparator<Paciente> comparadorPorApellidos = Comparator.comparing(paciente -> {
            // Puedes ajustar esta lógica según la estructura de tus datos y la forma en que se almacenan los apellidos
            String[] apellidos = paciente.nombre.split(" ");
            return apellidos[apellidos.length - 1];
        });

        // Ordenar la lista de pacientes utilizando el comparador personalizado
        Collections.sort(listaPacientes, comparadorPorApellidos);

        // Mostrar la lista ordenada
        System.out.println("****** LISTA DE PACIENTES ORDENADOS POR APELLIDOS ******");
        for (Paciente paciente : listaPacientes) {
            System.out.println("Nombre: " + paciente.nombre + " - Apellidos: " + obtenerApellidos(paciente.nombre));
        }
    }

    // Método auxiliar para obtener los apellidos
    private static String obtenerApellidos(String nombreCompleto) {
        String[] partesNombre = nombreCompleto.split(" ");
        if (partesNombre.length > 1) {
            // Devolver el último elemento como apellidos
            return partesNombre[partesNombre.length - 1];
        } else {
            // Si solo hay un nombre, devolver el nombre completo
            return nombreCompleto;
        }
    }

    private static void mostrarDoctorQueAtendioPaciente() {
        if (listaPacientes.isEmpty() || listaMedicos.isEmpty()) {
            System.out.println("No hay pacientes o médicos para mostrar.");
            return;
        }

        mostrarListaPacientes();

        System.out.print("Ingrese la posición del paciente para obtener el médico que lo atendió: ");
        int posicionPaciente = scanner.nextInt();

        if (posicionPaciente >= 1 && posicionPaciente <= listaPacientes.size()) {
            Paciente pacienteSeleccionado = listaPacientes.get(posicionPaciente - 1);
            Medico medicoQueAtendio = pacienteSeleccionado.medico;
            System.out.println("El paciente " + pacienteSeleccionado.nombre + " fue atendido por el médico " + medicoQueAtendio.nombre);
        } else {
            System.out.println("Posición inválida.");
        }
    }


    private static void buscarDoctoresPorEspecialidad() {
        if (listaMedicos.isEmpty()) {
            System.out.println("No hay médicos para mostrar.");
            return;
        }

        System.out.print("Ingrese la especialidad para buscar doctores: ");
        scanner.nextLine();  // Consumir el salto de línea pendiente
        String especialidadBuscada = scanner.nextLine();

        System.out.println("****** MÉDICOS POR ESPECIALIDAD ******");
        boolean medicosEncontrados = false;

        for (Medico medico : listaMedicos) {
            if (medico.especialidad.equalsIgnoreCase(especialidadBuscada)) {
                System.out.println("Número de Colegiatura: " + medico.numeroColegiatura);
                System.out.println("Nombre: " + medico.nombre);
                System.out.println("Especialidad: " + medico.especialidad);
                System.out.println("----------------------------------------");
                medicosEncontrados = true;
            }
        }

        if (!medicosEncontrados) {
            System.out.println("No se encontraron médicos con la especialidad: " + especialidadBuscada);
        }
    }


    private static void mostrarListaPacientes() {
        System.out.println("****** LISTA DE PACIENTES ******");
        for (int i = 0; i < listaPacientes.size(); i++) {
            Paciente paciente = listaPacientes.get(i);
            System.out.println((i + 1) + ". " + paciente.nombre + " - Médico: " + paciente.medico.nombre);
        }
    }
}