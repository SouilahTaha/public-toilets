# **Public Toilets Finder - Android App**  

## **Overview**  

**Public Toilets Finder** is an Android application that allows users to locate public toilets in **Paris**. It retrieves data from an **Open Data API** and presents an interactive list of toilets, including accessibility details. The app follows a **Clean Architecture** approach with **MVVM** and modern **Jetpack Compose** UI components for a seamless user experience.  

## **Features**  

✔ **Toilet Listing** – Displays a list of public toilets in Paris.  
✔ **Filtering** – Allows users to filter for **PRM-accessible** (reduced mobility) toilets.  
✔ **Location Awareness** – Uses device location to calculate **distance** (if permission granted).  
✔ **Pagination** – Optimized loading of data to prevent performance issues.  
✔ **Detail View** – Allows users to view more information about a specific toilet.  
✔ **Map View** – Visual representation of toilets on a map.  
✔ **Navigation Support** – Open a toilet’s address in a **map application** for directions.  
✔ **Multiple View Modes** – Switch between **list and map views**.  

---

## **Tech Stack**  

| **Technology**  | **Purpose**  |  
|----------------|-------------|  
| **Kotlin** | Primary programming language |  
| **Jetpack Compose** | Modern declarative UI |  
| **ViewModel** | Manages UI state |  
| **StateFlow** | Observes state changes |  
| **Navigation Component** | Handles in-app navigation |  
| **Coroutines** | Efficient background task handling |  
| **Hilt** | Dependency injection |  
| **MockK** | Mocking library for unit tests |  
| **JUnit** | Unit testing framework |  

---

## **Architecture**  

The app is structured using **Clean Architecture** with three distinct layers:  

1. **Presentation Layer**  
   - UI components (**Compose**) handle user interactions.  
   - **ViewModel** manages state & UI logic.  
   - Implements **debouncing & throttling** for search.  

2. **Domain Layer**  
   - Contains **Use Cases** (e.g., `GetPublicToiletsUseCase`).  
   - Independent from UI & data sources for **testability**.  
   - Handles **business logic** like filtering & sorting.  

3. **Data Layer**  
   - **Repository** interfaces define data access contracts.  
   - Implements **networking & caching(planed)**.  

---

## **Implemented Features**  

✅ **ToiletsView** fetches toilets list.  
✅ **Toilet Detail View** – Display full details about a selected toilet.  
✅ **Map Integration** – Show toilets on a **Google Maps** interface.  
✅ **Open in Maps** – Allow users to get directions.  
✅ **Loading State** shows progress when fetching data.  
✅ **Error Handling** displays errors for network issues.  
✅ **Pagination** to load more toilets efficiently.  
✅ **Filtering** based on **PRM accessibility**.  
✅ **Location-based Sorting** (if permissions granted).  
✅ **UI Events** allow event-driven state management.    
✅ **Dark/light Mode** easily switch between dark and light modes.  


---

## **Planned Enhancements**  

🔹 **Search Bar** – Find toilets by name or district.  
🔹 **Favorite Toilets** – Let users save preferred locations.  
🔹 **Offline Support** – Cache data for offline usage.  
🔹 **Design System module enhancment** – Ameliorate Design system module.  
🔹 **Better UI/UX** – Implement animations, and improved accessibility.  

---


## **Testing**  

✔ **Unit Tests** for ViewModels & UseCases.  
✔ **MockK** used for mocking dependencies.  
✔ **Coroutines tests** using `runTest()`.  

---

## **API & Data Source**  

- **API Endpoint:** [`Sanisettes Ville de Paris`](https://data.ratp.fr/api/records/1.0/search/?dataset=sanisettesparis2011)  
- **Dataset Info:** [`Sanisettes Dataset`](https://data.ratp.fr/explore/dataset/sanisettesparis2011/information/)  

---

## **Contact**  

For questions or contributions, feel free to reach out! 🚀  
