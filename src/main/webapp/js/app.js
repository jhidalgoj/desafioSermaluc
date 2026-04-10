const apiUrl = '/api/empleados';

function cargarEmpleados() {
  fetch(apiUrl)
    .then(res => res.json())
    .then(data => {
      const lista = document.getElementById('listaEmpleados');
      lista.innerHTML = '';
      data.forEach(e => {
        const li = document.createElement('li');
        li.textContent = `${e.id} - ${e.nombre} ${e.apellido} (${e.cargo})`;
        const btn = document.createElement('button');
        btn.textContent = 'Eliminar';
        btn.onclick = () => eliminarEmpleado(e.id);
        li.appendChild(btn);
        lista.appendChild(li);
      });
    });
}

function agregarEmpleado(event) {
  event.preventDefault();
  const empleado = {
    nombre: document.getElementById('nombre').value,
    apellido: document.getElementById('apellido').value,
    rut: document.getElementById('rut').value,
    cargo: document.getElementById('cargo').value,
    salario: parseFloat(document.getElementById('salario').value)
  };

  fetch(apiUrl, {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(empleado)
  }).then(() => cargarEmpleados());
}

function eliminarEmpleado(id) {
  fetch(`${apiUrl}?id=${id}`, { method: 'DELETE' })
    .then(() => cargarEmpleados());
}

document.getElementById('formEmpleado').addEventListener('submit', agregarEmpleado);
cargarEmpleados();
