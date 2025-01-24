document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューの設定
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

	// パートナーの有無に応じてボディクラスを設定
	const hasPartner = document.querySelector('.partner-info div') !== null;
	document.body.classList.add(hasPartner ? 'has-partner' : 'no-partner');

	// パートナーがいない場合のアニメーション
	const noPartnerMessage = document.querySelector('.no-partner .partner-info p');
	if (noPartnerMessage) {
		noPartnerMessage.style.opacity = '0';
		setTimeout(() => {
			noPartnerMessage.style.transition = 'opacity 1s ease';
			noPartnerMessage.style.opacity = '1';
		}, 100);
	}

	// パートナー情報がある場合のアニメーション
	const partnerInfo = document.querySelector('.has-partner .partner-info');
	if (partnerInfo) {
		partnerInfo.querySelectorAll('p').forEach((p, index) => {
			p.style.opacity = '0';
			p.style.transform = 'translateY(20px)';
			p.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
			setTimeout(() => {
				p.style.opacity = '1';
				p.style.transform = 'translateY(0)';
			}, 100 * (index + 1));
		});
	}

	// ボタンのホバーエフェクト強化
	const buttons = document.querySelectorAll('button');
	buttons.forEach(button => {
		button.addEventListener('mouseover', function() {
			this.style.transform = 'translateY(-3px)';
			this.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.2)';
		});

		button.addEventListener('mouseout', function() {
			this.style.transform = '';
			this.style.boxShadow = '';
		});
	});

	// 戻るボタンのホバーとクリックエフェクト
	const backButton = document.querySelector('.back-button');
	if (backButton) {
		backButton.addEventListener('mouseover', function() {
			this.style.transform = 'scale(1.1)';
			this.style.backgroundColor = '#357abd';
		});

		backButton.addEventListener('mouseout', function() {
			this.style.transform = '';
			this.style.backgroundColor = '#4a90e2';
		});

		backButton.addEventListener('mousedown', function() {
			this.style.transform = 'scale(0.95)';
		});

		backButton.addEventListener('mouseup', function() {
			this.style.transform = '';
		});
	}
});