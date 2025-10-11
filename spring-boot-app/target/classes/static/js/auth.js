/**
 * Authentication utilities for frontend
 */

// Authentication state management
function isLoggedIn() {
    const token = localStorage.getItem('authToken');
    if (!token) return false;
    
    try {
        const tokenData = parseJwt(token);
        const currentTime = Date.now() / 1000;
        
        // Check if token is expired
        if (tokenData.exp && tokenData.exp < currentTime) {
            logout();
            return false;
        }
        
        return true;
    } catch (error) {
        console.error('Error parsing token:', error);
        logout();
        return false;
    }
}

// Get current user info
function getCurrentUser() {
    const userInfo = localStorage.getItem('userInfo');
    return userInfo ? JSON.parse(userInfo) : null;
}

// Get auth token
function getAuthToken() {
    return localStorage.getItem('authToken');
}

// Logout function
function logout() {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userInfo');
    window.location.href = '/login';
}

// Parse JWT token
function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        
        return JSON.parse(jsonPayload);
    } catch (error) {
        console.error('Error parsing JWT:', error);
        return null;
    }
}

// Make authenticated API request
async function authenticatedFetch(url, options = {}) {
    const token = getAuthToken();
    
    if (!token) {
        throw new Error('No authentication token found');
    }
    
    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    };
    
    const mergedOptions = {
        ...defaultOptions,
        ...options,
        headers: {
            ...defaultOptions.headers,
            ...options.headers
        }
    };
    
    try {
        const response = await fetch(url, mergedOptions);
        
        // Handle unauthorized responses
        if (response.status === 401) {
            logout();
            throw new Error('Authentication failed');
        }
        
        return response;
    } catch (error) {
        if (error.message === 'Authentication failed') {
            throw error;
        }
        console.error('Fetch error:', error);
        throw new Error('Network error occurred');
    }
}

// Check if user has specific role
function hasRole(roleName) {
    const token = getAuthToken();
    if (!token) return false;
    
    try {
        const tokenData = parseJwt(token);
        const authorities = tokenData.authorities || [];
        return authorities.includes(`ROLE_${roleName}`);
    } catch (error) {
        console.error('Error checking role:', error);
        return false;
    }
}

// Check if user has specific permission
function hasPermission(permissionName) {
    const token = getAuthToken();
    if (!token) return false;
    
    try {
        const tokenData = parseJwt(token);
        const authorities = tokenData.authorities || [];
        return authorities.includes(permissionName);
    } catch (error) {
        console.error('Error checking permission:', error);
        return false;
    }
}

// Protect routes that require authentication
function requireAuth() {
    if (!isLoggedIn()) {
        window.location.href = '/login';
        return false;
    }
    return true;
}

// Protect routes that require specific role
function requireRole(roleName) {
    if (!requireAuth()) return false;
    
    if (!hasRole(roleName)) {
        showError('Access denied. Insufficient permissions.');
        return false;
    }
    return true;
}

// Update UI based on authentication state
function updateAuthUI() {
    const isAuthenticated = isLoggedIn();
    const user = getCurrentUser();
    
    // Update login/logout links
    const loginLink = document.querySelector('.login-link');
    const logoutLink = document.querySelector('.logout-link');
    const userInfo = document.querySelector('.user-info');
    
    if (loginLink) {
        loginLink.style.display = isAuthenticated ? 'none' : 'block';
    }
    
    if (logoutLink) {
        logoutLink.style.display = isAuthenticated ? 'block' : 'none';
    }
    
    if (userInfo && user) {
        userInfo.textContent = `Welcome, ${user.username}`;
        userInfo.style.display = isAuthenticated ? 'block' : 'none';
    }
    
    // Update navigation menu based on roles
    updateNavigation();
}

// Update navigation menu based on user roles
function updateNavigation() {
    if (!isLoggedIn()) return;
    
    const adminMenuItems = document.querySelectorAll('.admin-only');
    const managerMenuItems = document.querySelectorAll('.manager-only');
    const agentMenuItems = document.querySelectorAll('.agent-only');
    
    const isAdmin = hasRole('ADMIN');
    const isManager = hasRole('MANAGER');
    const isAgent = hasRole('AGENT');
    
    // Show/hide admin menu items
    adminMenuItems.forEach(item => {
        item.style.display = isAdmin ? 'block' : 'none';
    });
    
    // Show/hide manager menu items
    managerMenuItems.forEach(item => {
        item.style.display = (isAdmin || isManager) ? 'block' : 'none';
    });
    
    // Show/hide agent menu items
    agentMenuItems.forEach(item => {
        item.style.display = (isAdmin || isManager || isAgent) ? 'block' : 'none';
    });
}

// Show error message
function showError(message) {
    // Try to find existing error container
    let errorContainer = document.querySelector('.error-message');
    
    if (!errorContainer) {
        // Create error container if it doesn't exist
        errorContainer = document.createElement('div');
        errorContainer.className = 'error-message';
        errorContainer.style.cssText = `
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 4px;
            margin: 10px;
            border: 1px solid #f5c6cb;
        `;
        
        const main = document.querySelector('main') || document.body;
        main.insertBefore(errorContainer, main.firstChild);
    }
    
    errorContainer.textContent = message;
    errorContainer.style.display = 'block';
    
    // Auto-hide after 5 seconds
    setTimeout(() => {
        if (errorContainer) {
            errorContainer.style.display = 'none';
        }
    }, 5000);
}

// Show success message
function showSuccess(message) {
    // Try to find existing success container
    let successContainer = document.querySelector('.success-message');
    
    if (!successContainer) {
        // Create success container if it doesn't exist
        successContainer = document.createElement('div');
        successContainer.className = 'success-message';
        successContainer.style.cssText = `
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            border-radius: 4px;
            margin: 10px;
            border: 1px solid #c3e6cb;
        `;
        
        const main = document.querySelector('main') || document.body;
        main.insertBefore(successContainer, main.firstChild);
    }
    
    successContainer.textContent = message;
    successContainer.style.display = 'block';
    
    // Auto-hide after 3 seconds
    setTimeout(() => {
        if (successContainer) {
            successContainer.style.display = 'none';
        }
    }, 3000);
}

// Initialize authentication on page load
document.addEventListener('DOMContentLoaded', function() {
    // Update UI based on authentication state
    updateAuthUI();
    
    // Add logout event listener
    const logoutLink = document.querySelector('.logout-link');
    if (logoutLink) {
        logoutLink.addEventListener('click', function(e) {
            e.preventDefault();
            logout();
        });
    }
});

// Export functions for use in other scripts
window.auth = {
    isLoggedIn,
    getCurrentUser,
    getAuthToken,
    logout,
    parseJwt,
    authenticatedFetch,
    hasRole,
    hasPermission,
    requireAuth,
    requireRole,
    updateAuthUI,
    updateNavigation,
    showError,
    showSuccess
};