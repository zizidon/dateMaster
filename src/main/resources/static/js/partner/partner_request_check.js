document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューの制御（以前のコード同様）
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
		link.addEventListener('click', toggleMenu);
	});

	// ラブリーなハートアニメーション（改修版）
	function createLoveAnimation() {
		const container = document.querySelector('main');
		const maxHearts = 20; // 同時に表示するハートの最大数

		function createHeart() {
			// すでに最大数のハートがある場合は新しいハートを作成しない
			if (container.querySelectorAll('.floating-heart').length >= maxHearts) return;

			const heart = document.createElement('div');
			heart.innerHTML = '❤️';
			heart.classList.add('floating-heart');
			heart.style.position = 'absolute';
			heart.style.fontSize = `${10 + Math.random() * 20}px`;
			heart.style.color = '#ff69b4';
			heart.style.left = `${Math.random() * 100}%`;
			heart.style.top = `${Math.random() * 100}%`;
			heart.style.transform = `rotate(${Math.random() * 360}deg)`;
			heart.style.opacity = Math.random();
			heart.style.animation = 'floatHeart 3s ease-out';
			container.appendChild(heart);

			// 3秒後にハートを削除
			setTimeout(() => {
				heart.remove();
			}, 3000);
		}

		// 0.3秒ごとに新しいハートを作成
		setInterval(createHeart, 300);
	}

	// アニメーションを追加するCSSスタイル
	const style = document.createElement('style');
	style.textContent = `
        @keyframes floatHeart {
            0% { 
                transform: translateY(0) rotate(0deg);
                opacity: 1;
            }
            100% { 
                transform: translateY(-100px) rotate(360deg);
                opacity: 0;
            }
        }
    `;
	document.head.appendChild(style);

	createLoveAnimation();
});