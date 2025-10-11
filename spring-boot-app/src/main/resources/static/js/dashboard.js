// Dashboard JavaScript functionality

document.addEventListener('DOMContentLoaded', function() {
    loadDashboardStats();
});

// Load dashboard statistics
async function loadDashboardStats() {
    try {
        // Load flight count
        const flightsResponse = await fetch('/flights');
        if (flightsResponse.ok) {
            const flights = await flightsResponse.json();
            document.getElementById('flightCount').textContent = flights.length;
        } else {
            document.getElementById('flightCount').textContent = '0';
        }

        // Load airport count
        const airportsResponse = await fetch('/airports');
        if (airportsResponse.ok) {
            const airports = await airportsResponse.json();
            document.getElementById('airportCount').textContent = airports.length;
        } else {
            document.getElementById('airportCount').textContent = '0';
        }

        // Calculate active routes (unique departure-arrival combinations)
        if (flightsResponse.ok) {
            const flights = await flightsResponse.json();
            const routes = new Set();
            flights.forEach(flight => {
                routes.add(`${flight.departure_location}-${flight.arrival_location}`);
            });
            document.getElementById('activeRoutes').textContent = routes.size;
        } else {
            document.getElementById('activeRoutes').textContent = '0';
        }

    } catch (error) {
        console.error('Error loading dashboard stats:', error);
        document.getElementById('flightCount').textContent = 'Error';
        document.getElementById('airportCount').textContent = 'Error';
        document.getElementById('activeRoutes').textContent = 'Error';
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