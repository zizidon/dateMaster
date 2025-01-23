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
		link.addEventListener('click', toggleMenu);
	});


	// サウンドエフェクト
	//サウンドエフェクトは'/path/to/warning-sound.mp3'を実際の警告音サウンドファイルに置き換えてください
	function playWarningSound() {
		const audio = new Audio('/path/to/warning-sound.mp3');
		audio.play().catch(e => console.log('Audio play failed'));
	}

	// ページ読み込み時にサウンド再生（ブラウザのポリシーに注意）
	playWarningSound();
});