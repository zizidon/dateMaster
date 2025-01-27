document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューの制御
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

	// 星を生成する関数
	function createStars() {
		const main = document.querySelector('main');
		for (let i = 0; i < 50; i++) {
			const star = document.createElement('div');
			star.className = 'star';
			star.style.left = `${Math.random() * 100}%`;
			star.style.top = `${Math.random() * 100}%`;
			star.style.animationDelay = `${Math.random() * 2}s`;
			main.appendChild(star);
		}
	}

	// 魔法の輝きエフェクトを生成する関数
	function createMagicSparkles() {
		const container = document.querySelector('main');
		setInterval(() => {
			const sparkle = document.createElement('div');
			sparkle.className = 'magic-sparkle';
			sparkle.style.left = `${Math.random() * 100}%`;
			sparkle.style.top = `${Math.random() * 100}%`;
			container.appendChild(sparkle);

			setTimeout(() => {
				sparkle.remove();
			}, 3000);
		}, 500);
	}

	// 問題コンテナにホバーエフェクトを追加
	const coachingContainers = document.querySelectorAll('.coaching-container');
	coachingContainers.forEach(container => {
		container.addEventListener('mouseenter', () => {
			container.style.transform = 'translateY(-5px)';
		});
		container.addEventListener('mouseleave', () => {
			container.style.transform = 'translateY(0)';
		});
	});

	// 初期化
	createStars();
	createMagicSparkles();
});