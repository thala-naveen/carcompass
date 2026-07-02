// ─── app.js: Landing Page ───
$(function () {
  loadTrendingCars();
});

function formatPrice(price) {
  if (!price) return 'N/A';
  return '$' + Number(price).toLocaleString();
}

function starRating(rating) {
  const stars = Math.round(rating || 0);
  return '★'.repeat(stars) + '☆'.repeat(5 - stars);
}

function renderCarCard(car, matchPercent) {
  const tags = (car.tags || []).slice(0, 3).map(t => `<span class="car-tag">${t}</span>`).join('');
  const matchBadge = matchPercent != null
    ? `<span class="match-percent">${matchPercent}% match</span>`
    : '';

  // Use the first GridFS image if available, otherwise fallback to emoji
  const firstImageId = (car.imageGridFsIds && car.imageGridFsIds.length > 0) ? car.imageGridFsIds[0] : null;
  const imgHtml = firstImageId
    ? `<img src="/api/cars/images/${firstImageId}" alt="${car.make} ${car.model}"
            style="width:100%;height:100%;object-fit:cover;"
            onerror="this.parentElement.innerHTML='<span style=\\'font-size:3.5rem\\'>🚗</span>'" />`
    : `<span style="font-size:3.5rem">🚗</span>`;

  return `
    <div class="car-card" onclick="window.location='/car.html?id=${car.id}'">
      <div class="car-card-img" style="overflow:hidden;display:flex;align-items:center;justify-content:center;">${imgHtml}</div>
      <div class="car-card-body">
        <div class="car-card-title">${car.make} ${car.model}</div>
        <div class="car-card-variant">${car.variant || ''}</div>
        <div class="car-card-meta">
          <span class="car-price">${formatPrice(car.exShowroomPrice)}</span>
          <span class="car-rating">${starRating(car.avgRating)} <span style="color:var(--text-muted)">(${car.reviewCount || 0})</span></span>
        </div>
        <div class="car-tags">${tags} ${matchBadge}</div>
      </div>
    </div>`;
}

function loadTrendingCars() {
  $.get('/api/cars/trending', function (cars) {
    const $grid = $('#trendingCars');
    $grid.empty();
    if (!cars || cars.length === 0) {
      $grid.html('<p style="color:var(--text-muted); grid-column:1/-1">No cars found. Add some via the Admin API!</p>');
      return;
    }
    cars.slice(0, 6).forEach(car => $grid.append(renderCarCard(car)));
  }).fail(function () {
    // fallback: load all cars
    $.get('/api/cars', function (cars) {
      const $grid = $('#trendingCars');
      $grid.empty();
      if (!cars || cars.length === 0) {
        $grid.html('<p style="color:var(--text-muted); grid-column:1/-1">No cars found.</p>');
        return;
      }
      cars.slice(0, 6).forEach(car => $grid.append(renderCarCard(car)));
    });
  });
}
