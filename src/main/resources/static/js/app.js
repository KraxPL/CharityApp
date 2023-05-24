document.addEventListener("DOMContentLoaded", function() {

  /**
   * Form Select
   */
  class FormSelect {
    constructor($el) {
      this.$el = $el;
      this.options = [...$el.children];
      this.init();
    }

    init() {
      this.createElements();
      this.addEvents();
      this.$el.parentElement.removeChild(this.$el);
    }

    createElements() {
      // Input for value
      this.valueInput = document.createElement("input");
      this.valueInput.type = "text";
      this.valueInput.name = this.$el.name;

      // Dropdown container
      this.dropdown = document.createElement("div");
      this.dropdown.classList.add("dropdown");

      // List container
      this.ul = document.createElement("ul");

      // All list options
      this.options.forEach((el, i) => {
        const li = document.createElement("li");
        li.dataset.value = el.value;
        li.innerText = el.innerText;

        if (i === 0) {
          // First clickable option
          this.current = document.createElement("div");
          this.current.innerText = el.innerText;
          this.dropdown.appendChild(this.current);
          this.valueInput.value = el.value;
          li.classList.add("selected");
        }

        this.ul.appendChild(li);
      });

      this.dropdown.appendChild(this.ul);
      this.dropdown.appendChild(this.valueInput);
      this.$el.parentElement.appendChild(this.dropdown);
    }

    addEvents() {
      this.dropdown.addEventListener("click", e => {
        const target = e.target;
        this.dropdown.classList.toggle("selecting");

        // Save new value only when clicked on li
        if (target.tagName === "LI") {
          this.valueInput.value = target.dataset.value;
          this.current.innerText = target.innerText;
        }
      });
    }
  }
  document.querySelectorAll(".form-group--dropdown select").forEach(el => {
    new FormSelect(el);
  });

  /**
   * Hide elements when clicked on document
   */
  document.addEventListener("click", function(e) {
    const target = e.target;
    const tagName = target.tagName;

    if (target.classList.contains("dropdown")) return false;

    if (tagName === "LI" && target.parentElement.parentElement.classList.contains("dropdown")) {
      return false;
    }

    if (tagName === "DIV" && target.parentElement.classList.contains("dropdown")) {
      return false;
    }

    document.querySelectorAll(".form-group--dropdown .dropdown").forEach(el => {
      el.classList.remove("selecting");
    });
  });

  /**
   * Switching between form steps
   */
  class FormSteps {
    constructor(form) {
      this.$form = form;
      this.$next = form.querySelectorAll(".next-step");
      this.$prev = form.querySelectorAll(".prev-step");
      this.$step = form.querySelector(".form--steps-counter span");
      this.currentStep = 1;

      this.$stepInstructions = form.querySelectorAll(".form--steps-instructions p");
      const $stepForms = form.querySelectorAll("form > div");
      this.slides = [...this.$stepInstructions, ...$stepForms];

      this.init();
    }

    /**
     * Init all methods
     */
    init() {
      this.events();
      this.updateForm();
    }

    /**
     * All events that are happening in form
     */
    events() {
      // Next step
      this.$next.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
          this.currentStep++;
          this.updateForm();
        });
      });

      // Previous step
      this.$prev.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
          this.currentStep--;
          this.updateForm();
        });
      });

      // Form submit
      this.$form.querySelector("form").addEventListener("submit", e => this.submit(e));
    }

    /**
     * Update form front-end
     * Show next or previous section etc.
     */
    updateForm() {
      this.$step.innerText = this.currentStep;

      // TODO: Validation

      this.slides.forEach(slide => {
        slide.classList.remove("active");

        if (slide.dataset.step == this.currentStep) {
          slide.classList.add("active");
        }
      });

      this.$stepInstructions[0].parentElement.parentElement.hidden = this.currentStep >= 5;
      this.$step.parentElement.hidden = this.currentStep >= 5;


      const summaryItems = document.getElementById('summaryItems');
      const summaryStreet = document.getElementById('summaryStreet');
      const summaryCity = document.getElementById('summaryCity');
      const summaryZipCode = document.getElementById('summaryZipCode');
      const summaryPhone = document.getElementById('summaryPhone');
      const summaryPickUpDate = document.getElementById('summaryPickUpDate');
      const summaryPickUpTime = document.getElementById('summaryPickUpTime');
      const summaryPickUpComment = document.getElementById('summaryPickUpComment');

      const quantity = document.getElementById("quantity").value;
      const institutionId = document.querySelector('input#organizationId:checked');
      const institutionName = institutionId.parentElement.querySelector("div.title").textContent.trim().substring(10);
      const address = document.getElementById("address").value;
      const postCode = document.getElementById("postcode").value;
      const city = document.getElementById("city").value;
      const phone = document.getElementById("phone").value;
      const date = document.getElementById("data").value;
      const time = document.getElementById("time").value;
      const pickUpComment = document.getElementById("more_info").value || "Brak uwag";
      const checkboxes = document.querySelectorAll('.form-group--checkbox input[type="checkbox"]:checked');
      const categoryNames = Array.from(checkboxes).map(checkbox => checkbox.parentElement.querySelector('.categoryName').innerText);

      const items = document.querySelector('.summary ul');
      items.innerHTML = '';

      const summaryItem = document.createElement('li');
      const categoriesText = categoryNames.length === 1 ? 'w kategorii' : 'w kategoriach';
      const nounSuffix = quantity === '1' ? 'worek' : (quantity > '1' && quantity < '5') ? 'worki' : 'worków';

      summaryItem.innerHTML = `<span class="icon icon-bag"></span><span class="summary--text">${quantity} ${nounSuffix} ${categoriesText}: ${categoryNames.join(', ')}</span>`;
      items.appendChild(summaryItem);


      summaryItems.innerHTML += `<li><span class="icon icon-hand"></span><span class="summary--text">Dla fundacji "${institutionName}"</span></li>`;
      summaryStreet.textContent = address;
      summaryCity.textContent = city;
      summaryZipCode.textContent = postCode;
      summaryPhone.textContent = phone;
      summaryPickUpDate.textContent = date;
      summaryPickUpTime.textContent = time;
      summaryPickUpComment.textContent = pickUpComment;

    }

  }
  const form = document.querySelector(".form--steps");
  if (form !== null) {
    new FormSteps(form);
  }
});
