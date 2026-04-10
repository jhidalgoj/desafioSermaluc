package org.example.servlet;

import com.google.gson.Gson;
import org.example.dao.EmpleadoDAO;
import org.example.model.Empleado;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet para manejar las operaciones CRUD de empleados. Este servlet se encarga de recibir las solicitudes HTTP,
 * procesarlas y delegar las operaciones a la capa DAO. No contiene lógica de negocio ni validaciones, solo se
 * encarga de manejar las solicitudes y respuestas HTTP.
 */
/*@WebServlet("/empleados")*/
public class EmpleadoServlet extends HttpServlet {
    private final EmpleadoDAO dao = new EmpleadoDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<Empleado> empleados = dao.listar();
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(empleados));
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al listar empleados");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            BufferedReader reader = req.getReader();
            Empleado e = gson.fromJson(reader, Empleado.class);

            List<String> errores = new ArrayList<>();

            // Validación: RUT/DNI duplicado
            if (dao.existeRut(e.getRut())) {
                errores.add("RUT/DNI duplicado: " + e.getRut());
            }

            // Validación: salario base mínimo
            if (e.getSalario() < 400000) {
                errores.add("Salario base menor a $400,000");
            }

            if (e.getNombre() == null || e.getApellido() == null || e.getRut() == null) {
                errores.add("Datos inválidos");
            }

            // Validación: bonos <= 50% salario base
            if (e.getBono() > (e.getSalario() * 0.5)) {
                errores.add("Bono supera el 50% del salario base");
            }

            // Validación: descuentos <= salario base
            if (e.getDescuentos() > e.getSalario()) {
                errores.add("Descuentos mayores al salario base");
            }

            if (!errores.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("application/json");
                resp.getWriter().write(gson.toJson(errores));
                return;
            }

            dao.agregar(e);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al agregar empleado");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String idParam = req.getParameter("id");
            if (idParam == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID requerido");
                return;
            }
            int id = Integer.parseInt(idParam);
            dao.eliminar(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar empleado");
        }
    }
}
