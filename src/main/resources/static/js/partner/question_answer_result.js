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

	// 正解時の花火エフェクト
	if (document.querySelector('.result-container.correct')) {
		createFireworks();
		playCelebrationSound();
	}

	function createFireworks() {
		const fireworksContainer = document.querySelector('.fireworks');
		for (let i = 0; i < 20; i++) {
			setTimeout(() => {
				const firework = document.createElement('div');
				firework.className = 'firework';
				firework.style.left = Math.random() * 100 + '%';
				firework.style.top = Math.random() * 100 + '%';
				firework.style.animation = `explode ${1 + Math.random()}s forwards`;
				firework.style.backgroundColor = `hsl(${Math.random() * 360}, 100%, 50%)`;
				fireworksContainer.appendChild(firework);

				setTimeout(() => {
					firework.remove();
				}, 1000);
			}, i * 200);
		}
	}

	function playCelebrationSound() {
		// 音声効果を追加する場合はここに実装
	}

	// 不正解時のエフェクト
	if (document.querySelector('.result-container.incorrect')) {
		const container = document.querySelector('.result-container.incorrect');
		container.style.animation = 'shake 0.5s';
	}

	// シェイクアニメーションのスタイルを動的に追加
	const style = document.createElement('style');
	style.textContent = `
        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-10px); }
            75% { transform: translateX(10px); }
        }

        @keyframes explode {
            0% { 
                transform: scale(0);
                opacity: 1;
            }
            100% { 
                transform: scale(2);
                opacity: 0;
            }
        }

        .firework {
            position: absolute;
            width: 10px;
            height: 10px;
            border-radius: 50%;
            pointer-events: none;
        }
    `;
	document.head.appendChild(style);
});