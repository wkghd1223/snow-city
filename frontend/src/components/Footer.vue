<template>
    <footer class="app-footer">
      <div class="footer-content">
        <!-- Logo and Description -->
        <div class="footer-section">
          <img src="@/assets/logo.png" alt="Logo" class="footer-logo" />
        </div>
  
        <!-- Social Media -->
        <div class="footer-section">
          <div class="social-icons">
            <a href="https://github.com/your-repo" target="_blank" rel="noopener" aria-label="GitHub">
              <img src="@/assets/github-logo.png" alt="GitHub Logo" class="github-logo" />
            </a>
          </div>
        </div>
      </div>
  
      <!-- Footer Bottom -->
      <div class="footer-bottom">
        <p>&copy; {{ new Date().getFullYear() }} Snowy Destinations. All Rights Reserved.</p>
        <p class="footer-quotes">Original photo content by the developer.</p>
      </div>
    </footer>
  </template>
  
  <style scoped>
  .app-footer {
    background-color: #1c1c1c;
    color: #fff;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
  }
  
  .footer-content {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    gap: 20px;
    width: 100%;
    max-width: 1200px;
  }
  
  .footer-section {
    flex: 1;
    min-width: 200px;
  }
  
  .footer-logo {
    width: 100px;
    margin-bottom: 10px;
  }
  
  .footer-description {
    font-size: 14px;
    line-height: 1.6;
  }
  
  .social-icons a {
    display: inline-block;
    margin-right: 10px;
  }
  
  .github-logo {
    width: 24px;
    height: 24px;
    transition: transform 0.3s;
  }
  
  .github-logo:hover {
    transform: scale(1.2);
  }
  
  .footer-bottom {
    margin-top: 20px;
    text-align: center;
    font-size: 12px;
    border-top: 1px solid #333;
    padding-top: 10px;
  }
  
  .footer-quotes {
    font-style: italic;
    margin-top: 10px;
  }
  </style>
  