// ─── Auth Helpers ───
const AUTH = {
  getToken: () => localStorage.getItem('cc_token'),
  getEmail: () => localStorage.getItem('cc_email'),
  isLoggedIn: () => !!localStorage.getItem('cc_token'),
  set: (token, email) => { localStorage.setItem('cc_token', token); localStorage.setItem('cc_email', email); },
  clear: () => { localStorage.removeItem('cc_token'); localStorage.removeItem('cc_email'); },
  headers: () => ({ 'Authorization': 'Bearer ' + AUTH.getToken(), 'Content-Type': 'application/json' })
};

function showToast(msg, type = 'success') {
  let t = $('#toast');
  if (!t.length) $('body').append('<div class="toast" id="toast"></div>');
  t = $('#toast');
  t.text(msg).removeClass('success error').addClass(type).show();
  setTimeout(() => t.fadeOut(), 3000);
}

// ─── Update nav based on auth state ───
$(function () {
  if (AUTH.isLoggedIn()) {
    $('#authLinks').hide();
    $('#userArea').show();
    $('#userBadge').text(AUTH.getEmail());

    // Check if admin role - show admin link
    $.ajax({
      url: '/api/users/me',
      headers: AUTH.headers(),
      success: function (user) {
        if (user.roles && user.roles.includes('ADMIN')) {
          $('#adminLink').show();
        }
      }
    });
  }

  $('#logoutBtn').on('click', function (e) {
    e.preventDefault();
    AUTH.clear();
    showToast('Logged out', 'success');
    setTimeout(() => window.location.href = '/', 800);
  });
});
