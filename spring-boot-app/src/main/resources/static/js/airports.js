// Airports management JavaScript functionality

let allAirports = [];
let currentEditAirport = null;

document.addEventListener('DOMContentLoaded', function() {
    loadAirports();
    setupSearchFunctionality();
});

// Load all airports from API
async function loadAirports() {
    const loadingSpinner = document.getElementById('loadingSpinner');
    const airportsTable = document.getElementById('airportsTable');
    const noAirports = document.getElementById('noAirports');
    
    try {
        loadingSpinner.style.display = 'block';
        airportsTable.style.display = 'none';
        noAirports.style.display = 'none';

        const response = await fetch('/airports');
        if (response.ok) {
            allAirports = await response.json();
            displayAirports(allAirports);
        } else {
            throw new Error('Failed to load airports');
        }
    } catch (error) {
        console.error('Error loading airports:', error);
        showNotification('Error loading airports. Please try again.', 'danger');
        noAirports.style.display = 'block';
    } finally {
        loadingSpinner.style.display = 'none';
    }
}

// Display airports in table
function displayAirports(airports) {
    const tableBody = document.getElementById('airportsTableBody');
    const airportsTable = document.getElementById('airportsTable');
    const noAirports = document.getElementById('noAirports');

    if (airports.length === 0) {
        airportsTable.style.display = 'none';
        noAirports.style.display = 'block';
        return;
    }

    tableBody.innerHTML = '';
    airports.forEach(airport => {
        // Parse the location field to extract city and state/country
        const location = airport.airport_location || airport.location || 'N/A';
        const locationParts = location.split(',').map(part => part.trim());
        const city = locationParts[0] || 'N/A';
        const country = locationParts[1] || 'N/A';

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${airport.airport_number || airport.airportNumber || 'N/A'}</td>
            <td>${airport.airport_name || airport.airportName || 'N/A'}</td>
            <td>${location}</td>
            <td>${city}</td>
            <td>${country}</td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="editAirport('${airport.airport_number || airport.airportNumber}')">
                    <i class="fas fa-edit"></i> Edit
                </button>
                <button class="btn btn-danger btn-sm" onclick="deleteAirport('${airport.airport_number || airport.airportNumber}')">
                    <i class="fas fa-trash"></i> Delete
                </button>
            </td>
        `;
        tableBody.appendChild(row);
    });

    airportsTable.style.display = 'block';
    noAirports.style.display = 'none';
}

// Setup search functionality
function setupSearchFunctionality() {
    const searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('input', function() {
        const searchTerm = this.value.toLowerCase();
        const filteredAirports = allAirports.filter(airport =>
            (airport.airport_number && airport.airport_number.toLowerCase().includes(searchTerm)) ||
            (airport.airportNumber && airport.airportNumber.toLowerCase().includes(searchTerm)) ||
            (airport.airport_name && airport.airport_name.toLowerCase().includes(searchTerm)) ||
            (airport.airportName && airport.airportName.toLowerCase().includes(searchTerm)) ||
            (airport.airport_location && airport.airport_location.toLowerCase().includes(searchTerm)) ||
            (airport.location && airport.location.toLowerCase().includes(searchTerm))
        );
        displayAirports(filteredAirports);
    });
}

// Add new airport
async function addAirport() {
    const form = document.getElementById('addAirportForm');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    // Combine city and country into airport_location format
    const city = document.getElementById('city').value;
    const country = document.getElementById('country').value;
    const airport_location = `${city}, ${country}`;

    const airportData = {
        airport_number: document.getElementById('airportNumber').value,
        airport_name: document.getElementById('airportName').value,
        airport_location: airport_location
    };

    try {
        const response = await fetch('/airports', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(airportData)
        });

        if (response.ok) {
            showNotification('Airport added successfully!', 'success');
            const modal = bootstrap.Modal.getInstance(document.getElementById('addAirportModal'));
            modal.hide();
            form.reset();
            loadAirports();
        } else {
            throw new Error('Failed to add airport');
        }
    } catch (error) {
        console.error('Error adding airport:', error);
        showNotification('Error adding airport. Please try again.', 'danger');
    }
}

// Edit airport
function editAirport(airportNumber) {
    const airport = allAirports.find(a => 
        a.airport_number === airportNumber || a.airportNumber === airportNumber
    );
    if (!airport) return;

    currentEditAirport = airportNumber;
    
    // Parse the location field to extract city and state/country
    const location = airport.airport_location || airport.location || '';
    const locationParts = location.split(',').map(part => part.trim());
    const city = locationParts[0] || '';
    const country = locationParts[1] || '';
    
    document.getElementById('editAirportNumber').value = airport.airport_number || airport.airportNumber || '';
    document.getElementById('editAirportName').value = airport.airport_name || airport.airportName || '';
    document.getElementById('editLocation').value = location;
    document.getElementById('editCity').value = city;
    document.getElementById('editCountry').value = country;

    const modal = new bootstrap.Modal(document.getElementById('editAirportModal'));
    modal.show();
}

// Update airport
async function updateAirport() {
    if (!currentEditAirport) return;

    const form = document.getElementById('editAirportForm');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    // Combine city and country into airport_location format
    const city = document.getElementById('editCity').value;
    const country = document.getElementById('editCountry').value;
    const airport_location = `${city}, ${country}`;

    const airportData = {
        airport_number: document.getElementById('editAirportNumber').value,
        airport_name: document.getElementById('editAirportName').value,
        airport_location: airport_location
    };

    try {
        const response = await fetch(`/airports/${currentEditAirport}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(airportData)
        });

        if (response.ok) {
            showNotification('Airport updated successfully!', 'success');
            const modal = bootstrap.Modal.getInstance(document.getElementById('editAirportModal'));
            modal.hide();
            loadAirports();
        } else {
            throw new Error('Failed to update airport');
        }
    } catch (error) {
        console.error('Error updating airport:', error);
        showNotification('Error updating airport. Please try again.', 'danger');
    }
}

// Delete airport
async function deleteAirport(airportNumber) {
    if (!confirm(`Are you sure you want to delete airport ${airportNumber}?`)) {
        return;
    }

    try {
        const response = await fetch(`/airports/${airportNumber}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showNotification('Airport deleted successfully!', 'success');
            loadAirports();
        } else {
            throw new Error('Failed to delete airport');
        }
    } catch (error) {
        console.error('Error deleting airport:', error);
        showNotification('Error deleting airport. Please try again.', 'danger');
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