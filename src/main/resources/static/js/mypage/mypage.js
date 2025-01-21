document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューの制御
	const hamburgerButton = document.querySelector('.hamburger-button');
	const hamburgerMenu = document.querySelector('.hamburger-menu');

	// オーバーレイ要素を作成
	const overlay = document.createElement('div');
	overlay.className = 'overlay';
	document.body.appendChild(overlay);

	function toggleMenu() {
		hamburgerButton.classList.toggle('active');
		hamburgerMenu.classList.toggle('active');
		overlay.classList.toggle('active');

		// メニューが開いているときはスクロールを無効化
		if (hamburgerMenu.classList.contains('active')) {
			document.body.style.overflow = 'hidden';
		} else {
			document.body.style.overflow = '';
		}
	}

	hamburgerButton.addEventListener('click', toggleMenu);
	overlay.addEventListener('click', toggleMenu);

	// メニューリンクをクリックしたときにメニューを閉じる
	const menuLinks = document.querySelectorAll('.hamburger-menu a');
	menuLinks.forEach(link => {
		link.addEventListener('click', () => {
			toggleMenu();
		});
	});

	// ボタンのホバーエフェクト強化
	const buttons = document.querySelectorAll('button');
	buttons.forEach(button => {
		button.addEventListener('mouseenter', () => {
			button.style.transform = 'translateY(-2px)';
		});

		button.addEventListener('mouseleave', () => {
			button.style.transform = 'translateY(0)';
		});
	});

	// テーブルの行のホバーエフェクト
	const tableRows = document.querySelectorAll('.mypage-info table tr');
	tableRows.forEach(row => {
		row.addEventListener('mouseenter', () => {
			row.querySelector('td').style.backgroundColor = '#f0f0f0';
		});

		row.addEventListener('mouseleave', () => {
			row.querySelector('td').style.backgroundColor = '#f8f9fa';
		});
	});
});