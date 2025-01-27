document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニュー（以前のコードと同様）
	const hamburgerButton = document.querySelector('.hamburger-button');
	const hamburgerMenu = document.querySelector('.hamburger-menu');

	const overlay = document.createElement('div');
	overlay.className = 'overlay';
	document.body.appendChild(overlay);

	function toggleMenu() {
		hamburgerButton.classList.toggle('active');
		hamburgerMenu.classList.toggle('active');
		overlay.classList.toggle('active');

		document.body.style.overflow = hamburgerMenu.classList.contains('active') ? 'hidden' : '';
	}

	hamburgerButton.addEventListener('click', toggleMenu);
	overlay.addEventListener('click', toggleMenu);

	const menuLinks = document.querySelectorAll('.hamburger-menu a');
	menuLinks.forEach(link => {
		link.addEventListener('click', toggleMenu);
	});

	// 愛の星エフェクト
	function createLoveStars() {
		const body = document.body;
		setInterval(() => {
			const star = document.createElement('div');
			star.classList.add('star');
			star.textContent = '✨';
			star.style.left = `${Math.random() * 100}%`;
			star.style.animationDuration = `${3 + Math.random() * 3}s`;
			body.appendChild(star);

			setTimeout(() => {
				star.remove();
			}, 5000);
		}, 500);
	}
	createLoveStars();

	// 応援メッセージをランダムに表示（改善版）
	const encouragementMessages = [
		'愛は乗り越えられる！💕', 'パートナーシップは成長の旅！✨', '二人の絆を信じて！🌈',
		'愛は無限の可能性！💖', 'あなたなら大丈夫！🌟', '諦めないで！💪',
		'小さな一歩が大きな変化を生む！🚀', '困難は成長のチャンス！🌱', '愛は最高の力！💘',
		'二人の未来は輝いている！✨', '愛情は奇跡を起こす！🌠', '心の絆を大切に！💞',
		'相手の気持ちを理解しよう！💕', 'コミュニケーションが鍵！🔑', '互いを尊重し合おう！🤝',
		'愛は理解から生まれる！❤️', '前を向いて進もう！🌈', '二人の力は無敵！💥',
		'愛は最高の冒険！🚀', '一緒なら何でもできる！🌟', '成長は痛みを伴うもの💖',
		'愛は選択と努力！🌱', '相手の視点を大切に💕', '互いの違いを祝福しよう！🌈'
	];

	function createContinuousEncouragementMessages() {
		function showMessage() {
			const messageContainer = document.createElement('div');
			messageContainer.style.position = 'fixed';
			messageContainer.style.bottom = `${20 + Math.random() * 200}px`;
			messageContainer.style.left = `${Math.random() * (window.innerWidth - 200)}px`;
			messageContainer.style.backgroundColor = `rgba(${Math.random() * 255}, ${Math.random() * 100}, ${Math.random() * 180}, 0.8)`;
			messageContainer.style.color = 'white';
			messageContainer.style.padding = '10px 20px';
			messageContainer.style.borderRadius = '20px';
			messageContainer.style.zIndex = '1000';
			messageContainer.style.fontSize = `${14 + Math.random() * 6}px`;
			messageContainer.style.animation = 'heartBeat 1.5s infinite, fadeOut 3s';
			messageContainer.textContent = encouragementMessages[Math.floor(Math.random() * encouragementMessages.length)];

			document.body.appendChild(messageContainer);

			setTimeout(() => {
				document.body.removeChild(messageContainer);
			}, 3000);
		}

		// 初期表示
		showMessage();

		// 継続的にメッセージを表示
		setInterval(showMessage, 2000);
	}

	function showRandomEncouragement(event) {
		const messageFrequency = 5;
		const messageInterval = 300;

		for (let i = 0; i < messageFrequency; i++) {
			setTimeout(() => {
				const messageContainer = document.createElement('div');
				messageContainer.style.position = 'fixed';
				messageContainer.style.bottom = `${20 + Math.random() * 200}px`;
				messageContainer.style.left = `${Math.random() * (window.innerWidth - 200)}px`;
				messageContainer.style.backgroundColor = `rgba(${Math.random() * 255}, ${Math.random() * 100}, ${Math.random() * 180}, 0.8)`;
				messageContainer.style.color = 'white';
				messageContainer.style.padding = '10px 20px';
				messageContainer.style.borderRadius = '20px';
				messageContainer.style.zIndex = '1000';
				messageContainer.style.fontSize = `${14 + Math.random() * 6}px`;
				messageContainer.style.animation = 'heartBeat 1.5s infinite, fadeOut 3s';
				messageContainer.textContent = encouragementMessages[Math.floor(Math.random() * encouragementMessages.length)];

				document.body.appendChild(messageContainer);

				setTimeout(() => {
					document.body.removeChild(messageContainer);
				}, 3000);
			}, i * messageInterval);
		}
	}

	// ページ読み込み時から継続的にメッセージを表示
	createContinuousEncouragementMessages();


	// ボタンにホバーイベントを追加
	const coachingButtons = document.querySelectorAll('.coaching-form button');
	coachingButtons.forEach(button => {
		button.addEventListener('mouseenter', showRandomEncouragement);
	});

	// フェードアウトアニメーション用のCSSを追加
	const styleSheet = document.createElement("style");
	styleSheet.type = "text/css";
	styleSheet.innerText = `
	        @keyframes fadeOut {
	            0% { opacity: 1; transform: translateY(0); }
	            100% { opacity: 0; transform: translateY(-50px); }
	        }
	    `;
	document.head.appendChild(styleSheet);
});