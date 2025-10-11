// Flights management JavaScript functionality

let allFlights = [];
let currentEditFlight = null;

document.addEventListener('DOMContentLoaded', function() {
    loadFlights();
    setupSearchFunctionality();
});

// Load all flights from API
async function loadFlights() {
    const loadingSpinner = document.getElementById('loadingSpinner');
    const flightsTable = document.getElementById('flightsTable');
    const noFlights = document.getElementById('noFlights');
    
    try {
        loadingSpinner.style.display = 'block';
        flightsTable.style.display = 'none';
        noFlights.style.display = 'none';

        const response = await authenticatedFetch('/flights');
        if (response.ok) {
            allFlights = await response.json();
            displayFlights(allFlights);
        } else {
            throw new Error('Failed to load flights');
        }
    } catch (error) {
        console.error('Error loading flights:', error);
        showNotification('Error loading flights. Please try again.', 'danger');
        noFlights.style.display = 'block';
    } finally {
        loadingSpinner.style.display = 'none';
    }
}

// Display flights in table
function displayFlights(flights) {
    const tableBody = document.getElementById('flightsTableBody');
    const flightsTable = document.getElementById('flightsTable');
    const noFlights = document.getElementById('noFlights');

    if (flights.length === 0) {
        flightsTable.style.display = 'none';
        noFlights.style.display = 'block';
        return;
    }

    tableBody.innerHTML = '';
    flights.forEach(flight => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${flight.flight_number}</td>
            <td>${flight.airline_name}</td>
            <td>${flight.departure_location}</td>
            <td>${flight.arrival_location}</td>
            <td>${flight.departure_time}</td>
            <td>${flight.arrival_time}</td>
            <td>$${flight.flight_cost.toFixed(2)}</td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="editFlight('${flight.flight_number}')">
                    <i class="fas fa-edit"></i> Edit
                </button>
                <button class="btn btn-danger btn-sm" onclick="deleteFlight('${flight.flight_number}')">
                    <i class="fas fa-trash"></i> Delete
                </button>
            </td>
        `;
        tableBody.appendChild(row);
    });

    flightsTable.style.display = 'block';
    noFlights.style.display = 'none';
}

// Setup search functionality
function setupSearchFunctionality() {
    const searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('input', function() {
        const searchTerm = this.value.toLowerCase();
        const filteredFlights = allFlights.filter(flight =>
            flight.flight_number.toLowerCase().includes(searchTerm) ||
            flight.airline_name.toLowerCase().includes(searchTerm) ||
            flight.departure_location.toLowerCase().includes(searchTerm) ||
            flight.arrival_location.toLowerCase().includes(searchTerm)
        );
        displayFlights(filteredFlights);
    });
}

// Add new flight
async function addFlight() {
    const form = document.getElementById('addFlightForm');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const flightData = {
        flight_number: document.getElementById('flightNumber').value,
        airport_number: document.getElementById('airportNumber').value,
        departure_location: document.getElementById('departureLocation').value,
        departure_time: document.getElementById('departureTime').value,
        arrival_location: document.getElementById('arrivalLocation').value,
        arrival_time: document.getElementById('arrivalTime').value,
        airline_name: document.getElementById('airlineName').value,
        flight_cost: parseFloat(document.getElementById('flightCost').value)
    };

    try {
        const response = await authenticatedFetch('/flights', {
            method: 'POST',
            body: JSON.stringify(flightData)
        });

        if (response.ok) {
            showNotification('Flight added successfully!', 'success');
            const modal = bootstrap.Modal.getInstance(document.getElementById('addFlightModal'));
            modal.hide();
            form.reset();
            loadFlights();
        } else {
            throw new Error('Failed to add flight');
        }
    } catch (error) {
        console.error('Error adding flight:', error);
        showNotification('Error adding flight. Please try again.', 'danger');
    }
}

// Edit flight
function editFlight(flightNumber) {
    const flight = allFlights.find(f => f.flight_number === flightNumber);
    if (!flight) return;

    currentEditFlight = flightNumber;
    
    document.getElementById('editFlightNumber').value = flight.flight_number;
    document.getElementById('editAirportNumber').value = flight.airport_number;
    document.getElementById('editDepartureLocation').value = flight.departure_location;
    document.getElementById('editDepartureTime').value = flight.departure_time;
    document.getElementById('editArrivalLocation').value = flight.arrival_location;
    document.getElementById('editArrivalTime').value = flight.arrival_time;
    document.getElementById('editAirlineName').value = flight.airline_name;
    document.getElementById('editFlightCost').value = flight.flight_cost;

    const modal = new bootstrap.Modal(document.getElementById('editFlightModal'));
    modal.show();
}

// Update flight
async function updateFlight() {
    if (!currentEditFlight) return;

    const form = document.getElementById('editFlightForm');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const flightData = {
        flight_number: document.getElementById('editFlightNumber').value,
        airport_number: document.getElementById('editAirportNumber').value,
        departure_location: document.getElementById('editDepartureLocation').value,
        departure_time: document.getElementById('editDepartureTime').value,
        arrival_location: document.getElementById('editArrivalLocation').value,
        arrival_time: document.getElementById('editArrivalTime').value,
        airline_name: document.getElementById('editAirlineName').value,
        flight_cost: parseFloat(document.getElementById('editFlightCost').value)
    };

    try {
        const response = await authenticatedFetch(`/flights/${currentEditFlight}`, {
            method: 'PUT',
            body: JSON.stringify(flightData)
        });

        if (response.ok) {
            showNotification('Flight updated successfully!', 'success');
            const modal = bootstrap.Modal.getInstance(document.getElementById('editFlightModal'));
            modal.hide();
            loadFlights();
        } else {
            throw new Error('Failed to update flight');
        }
    } catch (error) {
        console.error('Error updating flight:', error);
        showNotification('Error updating flight. Please try again.', 'danger');
    }
}

// Delete flight
async function deleteFlight(flightNumber) {
    if (!confirm(`Are you sure you want to delete flight ${flightNumber}?`)) {
        return;
    }

    try {
        const response = await authenticatedFetch(`/flights/${flightNumber}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showNotification('Flight deleted successfully!', 'success');
            loadFlights();
        } else {
            throw new Error('Failed to delete flight');
        }
    } catch (error) {
        console.error('Error deleting flight:', error);
        showNotification('Error deleting flight. Please try again.', 'danger');
    }
}

// Utility function to show notifications
function showNotification(message, type = 'success') {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
    alertDiv.style.top = '20px';
    alertDiv.style.right = '20px';
    alertDiv.style.zIndex = '9999';
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    document.body.appendChild(alertDiv);
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        if (alertDiv.parentNode) {
            alertDiv.parentNode.removeChild(alertDiv);
        }
    }, 5000);
}