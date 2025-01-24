document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューの制御（partner_request.jsと同様）
	const hamburgerButton = document.querySelector('.hamburger-button');
	const hamburgerMenu = document.querySelector('.hamburger-menu');

	const overlay = document.createElement('div');
	overlay.className = 'overlay';
	document.body.appendChild(overlay);

	function toggleMenu() {
		hamburgerButton.classList.toggle('active');
		hamburgerMenu.classList.toggle('active');
		overlay.classList.toggle('active');

		if (hamburgerMenu.classList.contains('active')) {
			document.body.style.overflow = 'hidden';
		} else {
			document.body.style.overflow = '';
		}
	}

	hamburgerButton.addEventListener('click', toggleMenu);
	overlay.addEventListener('click', toggleMenu);

	const menuLinks = document.querySelectorAll('.hamburger-menu a');
	menuLinks.forEach(link => {
		link.addEventListener('click', () => {
			toggleMenu();
		});
	});

	// 雨のエフェクト
	function createRain() {
		const rainContainer = document.createElement('div');
		rainContainer.className = 'rain';
		document.body.appendChild(rainContainer);

		function createRaindrop() {
			const raindrop = document.createElement('div');
			raindrop.className = 'raindrop';

			raindrop.style.left = `${Math.random() * 100}%`;
			raindrop.style.animationDuration = `${0.7 + Math.random() * 1.5}s`;
			raindrop.style.opacity = `${0.4 + Math.random() * 0.6}`;

			rainContainer.appendChild(raindrop);

			setTimeout(() => {
				raindrop.remove();
			}, 2000);
		}

		// 50ミリ秒ごとに雨粒を生成
		const rainInterval = setInterval(createRaindrop, 50);

		// 一定時間後に雨の生成を停止
		setTimeout(() => {
			clearInterval(rainInterval);
		}, 20000);
	}

	// 画面読み込み時に雨のエフェクトを開始
	createRain();
});