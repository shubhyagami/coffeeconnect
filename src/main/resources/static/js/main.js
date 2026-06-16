document.addEventListener('DOMContentLoaded', function() {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.forEach(function(el) { new bootstrap.Tooltip(el); });
    var toastElList = [].slice.call(document.querySelectorAll('.toast'));
    toastElList.forEach(function(el) { new bootstrap.Toast(el); });
});
